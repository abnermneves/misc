#include <iostream>

using namespace std;

int ausenteOnOn(int* nums, unsigned int m){
    unsigned int i;
    bool identificado[m+1];

    for (i = 0; i <=m; i++)
        identificado[i] = false;

    for (i = 0; i < m; i++)
        identificado[nums[i]] = true;

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
