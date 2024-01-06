#include <iostream>
#include <windows.h>

int main(int argc, char* argv[])
{
    int size = atoi(argv[1]);
    long* arr = new long[size];
    for (int i = 0; i < size; i++) {
        arr[i] = atoi(argv[i + 2]);
    }
    
    //int X = atoi(argv[size + 4]);

    long sum = 0;
    for (int i = 0; i < size; i++)
        sum += arr[i] * arr[i];

    std::cout << "Sum of squares: " << sum << std::endl;
    Sleep(120);

    std::cin >> sum;

    delete[] arr;
    return 0;
}
