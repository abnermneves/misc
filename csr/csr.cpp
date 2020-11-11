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

int main () {
    unsigned int m;
    cin >> m;
    int nums[m+1];
    int resultado;

    for (unsigned int i = 0; i < m; i++)
        cin >> nums[i];
    nums[m] = -1;

    resultado = ausenteOnOn(nums, m);

    cout << "Falta: " << resultado << endl;
    return 0;
}
