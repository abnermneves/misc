#include <iostream>

using namespace std;

// complexidade O(n) tanto de espaço quanto de tempo
int ausenteOnOn(int* nums, unsigned int m){
    unsigned int i;
    bool identificado[m+1];

    // inicializando o vetor que marca quem foi identificado
    for (i = 0; i <= m; i++)
        identificado[i] = false;

    // marcando quem foi identificado
    for (i = 0; i < m; i++)
        identificado[nums[i]] = true;

    // encontrando quem não foi identificado
    for (i = 0; i <= m && identificado[i]; i++);

    return i;
}

// complexidades O(1) de espaço e O(n) de tempo
int ausenteO1On(int* nums, unsigned int m){
    unsigned int proximo = 0;
    int identificado = -1;
    int temp;
    unsigned int marcacoes = 0; // contador. vai fazer no total m marcações
    while (marcacoes < m){
        // se carro já está marcado
        if (identificado < 0){
            // encontra o próximo carro identificado ainda não marcado
            while (nums[proximo] < 0 && proximo < m)
                proximo++;

            identificado = nums[proximo];
            nums[proximo] = -1;

        }
        // marca o índice correspondente ao carro identificado
        temp = nums[identificado];
        nums[identificado] = -2;
        identificado = temp;
        marcacoes++;
    }

    // procura pelo carro não marcado e é ele a resposta
    unsigned int i;
    for (i = 0; i < m+1 && nums[i] != -1; i++);

    return i;
}

int main () {
    unsigned int m;
    cin >> m;
    int nums[m+1];
    int resultado;

    for (unsigned int i = 0; i < m; i++)
        cin >> nums[i];
    nums[m] = -1;

    resultado = ausenteO1On(nums, m);

    cout << "Falta: " << resultado << endl;
    return 0;
}
