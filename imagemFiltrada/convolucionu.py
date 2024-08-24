import matplotlib.pyplot as plt
from matplotlib.widgets import Slider
import numpy as np
import cv2

# DIMENSÕES DA IMAGEM
def recebeImagem(image):
    alt, base, canaisCor = image.shape
    print("Dimensões:\nAltura: ", alt, "\tLargura: ", base)
    print("Canais de cor: ", canaisCor)
    return alt, base, canaisCor

# FUNÇÃO IMPULSO
def impulso(a, size):
    n = np.arange(0, size)
    h = (1 - a) * (a ** n) * (n >= 0)  # h[n] = (1 - a) * (a**n) * u[n]
    return h

# CONSTRUÇÃO DA CONVOLUÇÃO MANUAL 2D
def convolucao(image, kernel):
    alt, base, canaisCor = recebeImagem(image)
    kernel_height, kernel_width = kernel.shape

    # Inicializa a saída com zeros
    output = np.zeros((alt, base, canaisCor), dtype=np.float32)

    # Padding da imagem para lidar com bordas
    pad_y = kernel_height // 2
    pad_x = kernel_width // 2
    padded_image = np.pad(image, ((pad_y, pad_y), (pad_x, pad_x), (0, 0)), mode='constant', constant_values=0)

    # Realizar a convolução manualmente para cada canal de cor
    for c in range(canaisCor):
        for y in range(alt):
            for x in range(base):
                region = padded_image[y:y + kernel_height, x:x + kernel_width, c]
                output[y, x, c] = np.sum(region * kernel)
    return np.clip(output, 0, 255).astype(np.uint8)

# CONVOLUÇÃO 1D
def convolucao_1d(signal, kernel):
    signal_length = len(signal)
    kernel_length = len(kernel)
    output = np.zeros(signal_length + kernel_length - 1)
    for i in range(len(output)):
        for j in range(kernel_length):
            if 0 <= i - j < signal_length:
                output[i] += signal[i - j] * kernel[j]
    return output

# DEFINIR KERNEL 2D
def definir_kernel():
    print("VAMOS DEFINIR UM FILTRO: ")
    print("1. Suavização")
    print("2. Bordas")
    op = int(input("opção: "))

    if op == 1:
        ker = np.array([[1, 1, 1],
                        [1, 1, 1],
                        [1, 1, 1]])
        ker = (1/9)*ker
    elif op == 2:
        ker = np.array([[-1, -1, -1],
                        [-1,  8, -1],
                        [-1, -1, -1]])
    else:
        print("Selecione um filtro válido")
        return None

    return ker

def mostraImagens(original, filtrada):
    plt.figure(figsize=(10, 5))
    plt.subplot(1, 2, 1)
    plt.imshow(cv2.cvtColor(original, cv2.COLOR_BGR2RGB))
    plt.title("Imagem Original")
    plt.axis('off')

    plt.subplot(1, 2, 2)
    plt.imshow(cv2.cvtColor(filtrada, cv2.COLOR_BGR2RGB))
    plt.title("Imagem Filtrada")
    plt.axis('off')

    plt.show()

def histograma(sinal_original, sinal_convoluido, kernel_1d):
    plt.figure(figsize=(10, 5))

    plt.plot(sinal_original, linestyle='-', marker='o', color='blue', label='Sinal Original')
    plt.plot(kernel_1d, linestyle='-', marker='x', color='green', label='Kernel (Impulso)')
    plt.plot(sinal_convoluido, linestyle='-', marker='s', color='red', label='Sinal Convoluído')

    plt.title("Sobreposição de Sinal Original, Kernel e Sinal Convoluído")
    plt.xlabel("Índice")
    plt.ylabel("Amplitude")
    plt.grid(True)
    plt.legend()

    plt.tight_layout()
    plt.show()

# Função para atualizar o gráfico
def update(val):
    a = slider.val  # Acessar o valor do slider
    kernel_1d = impulso(a, 10)
    sinal_convoluido = convolucao_1d(sinal_original, kernel_1d)
    line_kernel.set_ydata(kernel_1d)
    line_convolucao.set_ydata(sinal_convoluido)
    ax.relim()
    ax.autoscale_view()
    fig.canvas.draw_idle()

def main():
    # Parte 1: Convolução 2D
    image = cv2.imread("./imgs/dogguinho.jpg")

    if image is None:
        print("Erro ao carregar imagem")
        return

    recebeImagem(image)

    ker = definir_kernel()
    if ker is None:
        return

    image_filtrada = convolucao(image, ker)

    recebeImagem(image_filtrada)

    mostraImagens(image, image_filtrada)

    # Parte 2: Convolução 1D e Histogramas
    global sinal_original
    sinal_original = np.random.normal(0, 1, 100)

    # Criar o gráfico inicial
    global fig, ax, line_kernel, line_convolucao, slider
    fig, ax = plt.subplots(figsize=(10, 5))
    plt.subplots_adjust(left=0.1, bottom=0.25)

    # Inicializar valores
    a_initial = 0.5
    kernel_1d = impulso(a_initial, 10)
    sinal_convoluido = convolucao_1d(sinal_original, kernel_1d)

    # Plotar sinais
    ax.plot(sinal_original, linestyle='-', marker='o', color='blue', label='Sinal Original')
    line_kernel, = ax.plot(kernel_1d, linestyle='-', marker='x', color='green', label='Kernel (Impulso)')
    line_convolucao, = ax.plot(sinal_convoluido, linestyle='-', marker='s', color='red', label='Sinal Convoluído')

    # Configurar o gráfico
    ax.set_title("Sobreposição de Sinal Original, Kernel e Sinal Convoluído")
    ax.set_xlabel("Índice")
    ax.set_ylabel("Amplitude")
    ax.grid(True)
    ax.legend()

    # Adicionar slider para controlar `a`
    ax_slider = plt.axes([0.1, 0.1, 0.65, 0.03], facecolor='lightgoldenrodyellow')
    slider = Slider(ax_slider, 'a', 0.01, 1.0, valinit=a_initial)

    # Adicionar evento de atualização
    slider.on_changed(update)

    plt.show()

if __name__ == "__main__":
    main()

