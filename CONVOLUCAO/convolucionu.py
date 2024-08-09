import numpy as np
import matplotlib.pyplot as plt
import cv2

# DIMENSÕES DA IMAGEM
def recebeImagem(image):
    # Altura, largura, canais de cor
    h, base, canaisCor = image.shape
    print("Dimensões:\nAltura: ", h, "\tLargura: ", base)
    print("Canais de cor: ", canaisCor)
    return h, base, canaisCor

# FUNÇÃO IMPULSO
def impulso(a, size):
    n = np.arange(0, size)  # Corrigido np.arange
    h = (1 - a) * (a ** n) * (n >= 0)   # h[n] = (1 - a) * (a**n) * u[n]
    return h

# CONSTRUIR CONVOLUÇÃO MANUAL
def convolucao(image, kernel):
    h, base, canaisCor = recebeImagem(image)
    kernel_height, kernel_width = kernel.shape

    # Inicializa a saída com zeros
    output = np.zeros((h, base, canaisCor), dtype=np.float32)

    # Padding da imagem para lidar com bordas
    pad_y = kernel_height // 2
    pad_x = kernel_width // 2
    padded_image = np.pad(image, ((pad_y, pad_y), (pad_x, pad_x), (0, 0)), mode='constant', constant_values=0)

    # Realizar a convolução manualmente para cada canal de cor
    for c in range(canaisCor):
        for y in range(h):
            for x in range(base):
                # Extrair a região da imagem correspondente ao kernel
                region = padded_image[y:y + kernel_height, x:x + kernel_width, c]
                # Aplicar o kernel na região
                output[y, x, c] = np.sum(region * kernel)
    # retorna valores de saida de cor no intervalo de (0-255) e no tamanho de inteiros de 8 bits
    return np.clip(output, 0, 255).astype(np.uint8)

# DEFINIR KERNEL
def definir_kernel():
    print("VAMOS DEFINIR UM FILTRO: ")
    print("1. Suavização")
    print("2. Bordas")
    op = int(input("opção: "))

    if op == 1:
        ker  =  np.array([  [1, 1, 1],
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
    # Exibir as duas imagens lado a lado
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

def main():
    image = cv2.imread("./CONVOLUCAO/imgs/dogguinho.jpg")

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

if __name__ == "__main__":
    main()
