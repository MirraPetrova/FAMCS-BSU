#include <iostream>
#include <string>
#include <windows.h>
using namespace std;

HANDLE Reader[2];
wchar_t const* mes[2]{ L"rC", L"rD" };
wchar_t const* EMes[2]{ L"1", L"0" };

int main()
{
    string number;
    HANDLE hSemaphore = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, L"Semaphore");
    WaitForSingleObject(hSemaphore, INFINITE);
    cout << "Process reader is active" << endl;
    HANDLE mesEv[2];
    HANDLE mesEx[2];
    for (int i = 0; i < 2; i++) { mesEv[i] = OpenEvent(SYNCHRONIZE, FALSE, mes[i]); }
    for (int i = 0; i < 2; i++) { mesEx[i] = OpenEvent(EVENT_ALL_ACCESS, FALSE, EMes[i]); }
    while (true)
    {
        DWORD m = WaitForMultipleObjects(2, mesEv, FALSE, INFINITE);
        //SetEvent(Reader[m]);
        wcout << mes[m] << L'\n';
        cout << "Input 1 to exit or 0 to continue: ";
        cin >> number;
        wstring wline(number.begin(), number.end());
        if (wline._Equal(EMes[0])) { SetEvent(mesEx[0]); break; }
        if (wline._Equal(EMes[1])) { SetEvent(mesEx[1]); }
    }
    ReleaseSemaphore(hSemaphore, 1, NULL);
    return 0;
}