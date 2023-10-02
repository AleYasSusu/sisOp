# Trabalho de Sistemas Operacionais

## Informações Gerais
- Disciplina: Sistemas Operacionais
- Período: 2023/2
- Professor: Fernando Luís Dotti
- Instituição: PUCRS - Escola Politécnica
- Aluno: Alessandro Borges de Souza
## Visão Geral
Este projeto implementa um sistema operacional dividido em duas partes: Trabalho 1A e Trabalho 1B, com foco na implementação de gerentes de memória. As duas partes do projeto incluem funcionalidades relacionadas à alocação e desalocação de memória física para processos.

## Trabalho 1A - Gerente de Memória com Partições Fixas e Paginação
### Valores Básicos
- A memória possui um total de `tamMem` palavras.
- O tamanho de uma partição é definido pela constante `tamPart`.

### Funcionalidades
- Alocação de Partição: Permite a alocação de uma partição na memória, retornando o número da partição alocada.
- Desalocação de Partição: Libera a partição especificada.

## Trabalho 1A - Gerente de Memória com Paginação
### Valores Básicos
- O tamanho de Página é definido por `tamPg` palavras (ou posições de memória).
- O número de frames da memória é calculado como `tamMem / tamPg`.
- O tamanho de um frame é `tamFrame = tamPg`.

### Funcionalidades
- Alocação de Memória por Página: Verifica se a alocação de memória para um número específico de palavras é possível e, em caso afirmativo, retorna o conjunto de frames alocados.
- Desalocação de Memória por Página: Libera os frames alocados para um processo.

## Trabalho 1.3 - Carga
- O carregamento de programas é realizado a partir do início da partição atribuída pelo Gerente de Memória.

## Trabalho 1.4 - Tradução de Endereço e Proteção de Memória
- A tradução de endereço é realizada para garantir que os endereços lógicos sejam traduzidos para endereços físicos antes de acessar a memória.

## Trabalho 1.5 - Testes e Demonstração
- Os testes e demonstrações podem ser realizados juntamente com o Gerente de Processos (Trabalho 1B).
- Os comandos possíveis incluem a criação de processos, remoção de processos, listagem de processos, visualização do conteúdo do PCB, listagem da memória e execução de processos.

## Executando o Projeto
Recomendamos o uso da IDE Visual Studio Code para executar este projeto.

### Trabalho 1A
- Execute a classe principal correspondente para acessar as funcionalidades do Gerente de Memória com Partições Fixas ou Gerente de Memória com Paginação.

## Gerente de Processos (Trabalho 1B)
O Gerente de Processos (GP) é um módulo do sistema operacional responsável por gerenciar os processos em execução na máquina virtual. Nesta fase do projeto, o GP possui as seguintes funcionalidades:

### Funcionalidades
1. **Criar um Processo**
   - Dado um programa passado como parâmetro, o GP cria um processo.
   - Verifica o tamanho do programa.
   - Solicita alocação de memória ao Gerente de Memória.
   - Se não há memória disponível, retorna negativo.
   - Cria o PCB (Process Control Block) para o processo.
   - Define a partição de memória usada no PCB.
   - Carrega o programa na memória.
   - Define os demais parâmetros do PCB (ID, PC=0, etc).
   - Coloca o PCB na fila de processos prontos.
   - Retorna verdadeiro (true).

2. **Desalocar um Processo**
   - Dado o ID de um processo, o GP desaloca todo o espaço de memória associado a esse processo.
   - Remove o processo de qualquer fila em que esteja.
   - Desaloca o PCB associado ao processo.

### Estruturas de Dados
Nesta fase de evolução do sistema, as seguintes estruturas são necessárias:

- **PCB (Process Control Block):** Estrutura de descrição de processo contendo informações necessárias para a gerência do processo no sistema.

- **Running (Rodando):** Apenas um processo pode estar em execução em um determinado momento. O sistema mantém uma variável chamada "running" que identifica o PCB do processo em execução.

- **Ready (Aptos):** Existe uma lista de processos aptos para rodar, ou seja, uma lista de PCBs que aguardam para serem executados.

### Funcionamento e Testes
O sistema opera de forma iterativa, aguardando comandos para reagir. A cada comando recebido, o sistema executa a ação correspondente e aguarda o próximo comando. Os comandos disponíveis são:

- `cria <nomeDePrograma>`: Cria um processo com memória alocada, PCB, etc. e o coloca em uma lista de processos. Retorna um identificador único do processo no sistema (por exemplo, 1, 2, 3 ...).

- `listaProcessos`: Lista todos os processos existentes.

- `dump <id>`: Lista o conteúdo do PCB e o conteúdo da partição de memória do processo com o ID especificado.

- `desaloca <id>`: Remove o processo com o ID especificado do sistema, independentemente de ter sido executado ou não.

- `dumpM <inicio, fim>`: Lista o conteúdo da memória entre as posições de início e fim, independentemente do processo.

- `executa <id>`: Executa o processo com o ID fornecido. Se não houver processo com o ID especificado, retorna um erro.

- `traceOn`: Ativa o modo de execução em que a CPU imprime cada instrução executada.

- `traceOff`: Desativa o modo de execução mencionado anteriormente.

- `exit`: Encerra o sistema.

Os nomes dos comandos podem ser personalizados, desde que realizem as ações descritas acima.

## Executando o Projeto
Recomenda-se o uso de uma IDE, como o Visual Studio Code, para executar este projeto. Certifique-se de seguir as instruções de execução fornecidas nas interfaces do Gerente de Memória e do Gerente de Processos para interagir com o sistema de acordo com as funcionalidades desejadas.


