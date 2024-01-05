#include <iostream>
#include <thread>
#include <windows.h>
#include <string>
using namespace std;

HANDLE hEvent;
CRITICAL_SECTION cs;

void workThread(char* arr, int size) {
    int interval;
    cout << "������� ��������� �������� ��� ������ ����� ���������� ������ �������� � �������: ";
    cin >> interval;

    //cout << "Enter by thread work" << endl;
    for (int i = 0; i < size; i++) {
        if (isdigit(arr[i])) {
            arr[i] = arr[i];
        }
        else {
            arr[i] = '_';
        }
        Sleep(interval);
    }

    SetEvent(hEvent);
}

void countElementThread(char* arr, int size,int *count) {
    EnterCriticalSection(&cs);
    //cout << "Enter by thread countelement" << endl;

    WaitForSingleObject(hEvent, INFINITE);

    for (int i = 0; i < size; i++) {
        if (arr[i] != '_') {
            (*count)++;
        }
    }
    LeaveCriticalSection(&cs);
}

int main() {
    setlocale(LC_ALL, "rus");
    int size;
    cout << "������� ����������� �������: ";
    cin >> size;
    char* arr = new char[size];
    cout << "��������� ������ ����������:" << endl;
    for (int i = 0; i < size; i++) {
        cin >> arr[i];
    }
    cout << "�������� ������:" << endl;
    for (int i = 0; i < size; i++) {
        cout << arr[i] << " ";
    }
    cout << endl;


    hEvent = CreateEvent(NULL, TRUE, FALSE, NULL);
    InitializeCriticalSection(&cs);

    int* count;
    count = new int[1];
    *count = 0;
    thread work(workThread, arr, size);
    thread countElement(countElementThread, arr, size, count);

    WaitForSingleObject(hEvent, INFINITE);
    cout << "�������� ������:" << endl;
    for (int i = 0; i < size; i++) {
        cout << arr[i] << " ";
    }
    cout << endl;

    EnterCriticalSection(&cs);
    cout << "��������� ������ ������ CountElement: " << *count << endl;
    LeaveCriticalSection(&cs);
    
    work.join();
    countElement.join();

    CloseHandle(hEvent);
    DeleteCriticalSection(&cs);
    delete[] arr;

    return 0;
}