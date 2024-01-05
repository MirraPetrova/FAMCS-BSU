#include <iostream>
#include <windows.h>
using namespace std;

wchar_t const* mes[2]{ L"wA", L"wB" };

int main()
{
    string line;
    HANDLE hMutex = OpenMutex(MUTEX_ALL_ACCESS, FALSE, L"Mutex");
    WaitForSingleObject(hMutex, INFINITE);
    cout << "Process writer is active" << endl;
    HANDLE mesEv[2];
    for (int i = 0; i < 2; i++) { mesEv[i] = OpenEvent(EVENT_MODIFY_STATE, FALSE, mes[i]); }
    HANDLE mesEx[1];
    mesEx[0] = OpenEvent(EVENT_ALL_ACCESS, FALSE, L"1");
    while (true)
    {
        cout << "Enter A or B: " << endl;
        cin >> line;
        line = "w" + line;
        wstring wline(line.begin(), line.end());
        if (wline._Equal(mes[0])) { SetEvent(mesEv[0]); }
        if (wline._Equal(mes[1])) { SetEvent(mesEv[1]); }
        if (!wline._Equal(mes[0]) && !wline._Equal(mes[1])) { SetEvent(mesEx[0]); break; }
    }
    ReleaseMutex(hMutex);
    return 0;
}