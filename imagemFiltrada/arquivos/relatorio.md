## Relatório do Trabalho: Convolução e Análise de Imagens

#### professor: Bernardo Barancelli Shwedersky

#### aluna: Fernanda Polga Souza ( 24104492 )

## INTRODUÇÃO

Este relatório descreve a implementação e análise de um processo de convolução aplicado ao processamento de imagens.
O objetivo principal foi aprofundar a compreensão sobre como a convolução pode ser utilizada para aplicar diferentes filtros em imagens digitais, como suavização e detecção de bordas, sem o uso de bibliotecas de processamento de imagem de alto nível. Este trabalho teve como base os conceitos teóricos estudados em sala de aula e os aplicou em um ambiente de programação, utilizando Python.

## METODOLOGIA

O projeto era divido em duas frentes:
- Apresentar uma matriz filtro para convoluir com uma imagem(matriz imagem) e retornar uma imagem filtrada.
- Apresentar o gráfico discretizado dessa operação, estudando a influência de mudança do valor de `a`.
  - EXTRA: Acrescentar um Slider para mais dinamismo no controle e visualização do histograma.

## DESCRIÇÃO

A convolução é uma operação matemática que combina um sinal ou imagem com um filtro para modificar a entrada de acordo com a função do filtro. Na convolução 1D, o filtro é aplicado a um sinal unidimensional, ajustando os valores do sinal para realizar tarefas como suavização ou detecção de padrões. Na convolução 2D, o filtro é aplicado sobre uma imagem, alterando pixels e seus vizinhos para aplicar efeitos como borramento, nitidez e detecção de bordas. Esse processo permite ajustar a entrada para obter a saída desejada, seja para processamento de áudio ou imagens.

 Nesse projeto lidaremos com a convolução de imagens apenas e dois tipos de filtros (kernels). A convolução 2D foi realizada utilizando um filtro sobre uma imagem. O filtro pode ser selecionado para realizar operações como suavização ou detecção de bordas. 
- Filtro de Suavização: Reduz a intensidade das bordas e suaviza a imagem.
- Filtro de Detecção de Bordas: Realça as bordas, destacando mudanças abruptas na intensidade da imagem.
A função de convolução 2D é implementada manualmente sem o uso de bibliotecas de processamento de imagem. A imagem é filtrada aplicando o kernel sobre a imagem original, e a imagem resultante é exibida para análise.

A convolução 1D é utilizada para analisar o efeito de um filtro de impulso discreto em um sinal unidimensional. O filtro é definido pela função impulso com um parâmetro variável `a`.

Função Impulso e Parâmetro `a`
A função impulso é definida como:

	h[n] = (1 - a) * (a**n) * u[n]h
 
onde:
- `h[n]`: valor da função impulso em um índice específico `n`.
- `a`: o parâmetro que controla a forma da função impulso.
- `u[n]`: função unitária que é 1 para `n = 0` e 0 para `n < 0`. Isso garante que a função impulso é zero antes de `n = 0`.
  
A convolução entre um sinal e a função impulso resulta em uma forma modificada do sinal original. Assim, a forma da função impulso (controlada por `a`) afeta diretamente a saída da convolução:

- Se `a` é Pequeno: O sinal convoluído será mais suavizado, pois a resposta ao impulso não se estende muito e a convolução com um impulso concentrado resulta em uma forma de sinal que não é muito prolongada. A função impulso se torna mais concentrada em torno de `n = 0`. O valor da função `h[n]` rapidamente diminui com o aumento de `n`.

- Se `a` é Grande: A convolução com um impulso mais espalhado resulta em um sinal convoluído que é mais prolongado e tem uma resposta mais gradual.
A função impulso se espalha mais e decai mais lentamente. O valor de `h[n]` diminui mais lentamente conforme `n` aumenta.

## CONCLUSÃO

A análise e implementação da convolução 2D e 1D proporcionaram uma visão prática de como filtros podem ser aplicados a imagens e sinais, além de permitir uma compreensão maior do que é de fato a teoria que vemos em sala, agora no prático. O parâmetro `a` da função impulso desempenha um papel crucial na determinação da forma da resposta ao impulso e, consequentemente, na forma do sinal convoluído. A visualização interativa com o slider permite observar essas mudanças e entender como diferentes valores de `a` afetam a resposta do sistema. Foi uma tarefa intrigante e a princípio desafiadora, pois foi a minha primeira interação modificando imagens por código, mas ainda assim genial aprender sobre novas bibliotecas como o cv2 e descobrir mais funcionalidades em outras já familiares como o matplotlib e o numpy. Acho que o maior problema no geral foi lidar com o tamanho da imagem, pois ela acaba se transformando em uma matriz, então para loopings, cada pixel é percorrido, tornando o processo mais rápido ou demorado dependendo da dimensão total da imagem.

  
   
