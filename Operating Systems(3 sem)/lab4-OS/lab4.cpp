/*Написать программы для консольного процесса Administrator и консольных процессов Reader и Writer.  Для моделирования передачи сообщений ввести специальные события, которые обозначают сообщение «А» , «B», «C» , «D»    и конец сеанса для процессов Reader и Writer. Для сообщений «C» и  «D» использовать  события c ручным сбросом.
Одновременно принимать и отправлять сообщения могут только два АКТИВНЫХ процесса Writer(использовать мьютексы) и два АКТИВНЫХ процесса Reader(использовать семафор), передача остальных сообщений от других процессов должна временно блокироваться (находиться в режиме ожидания).
Процесс Administrator:
1.Инициализация объектов синхронизации;
2.запрашивает у пользователя количество процессов Writer( Reader);
3.запрашивает у пользователя кол-во отправленных сообщений для процессов Writer и кол-во полученных сообщений Reader (общее количество отправленных и принятых сообщений должно совпадать);
4.запускает заданное количество процессов Reader и Writer;
5.принимает от каждого процесса Writer сообщения и выводит на консоль, затем отправляет их процессам Reader.
6.принимает от каждого процесса Reader и Writer сообщение о завершении  сеанса и выводит его на консоль в одной строке. 
7.завершает свою работу.

Процесс Writer:
1.синхронизировать работу процессов Writer с помощью мьютексов
2.передачу сообщений реализовать с помощью событий
3.запрашивает с консоли сообщения, состоящее из “A” или  “B”,  и передает их (по одному) процессу Administrator;
4.передает сообщение о завершении  сеанса процессу Administrator;
5.завершает свою работу.

Процесс Reader:
1.синхронизировать работу процессов Reader с помощью семафора
2.передачу сообщений реализовать с помощью событий
3.принимает сообщения «C» , «D»   от  процесса Administrator;
4.выводит на консоль сообщения;
5.передает сообщение о завершении  сеанса процессу Administrator;
6.завершает свою работу.
*/
#include <iostream>
#include <windows.h>
using namespace std;

HANDLE Writer[2];
HANDLE Reader[2];
HANDLE End[2];
wchar_t const* WriterMes[2]{ L"wA", L"wB" };
wchar_t const* ReaderMes[2]{ L"rC", L"rD" };
wchar_t const* EndMes[2]{ L"1", L"0" };

int main()
{
    for (int i = 0; i < 2; i++)
    {
        Writer[i] = CreateEvent(NULL, FALSE, FALSE, WriterMes[i]);
        Reader[i] = CreateEvent(NULL, TRUE, FALSE, ReaderMes[i]);
    }

    for (int i = 0; i < 2; i++) { 
        End[i] = CreateEvent(NULL, FALSE, FALSE, EndMes[i]); 
    }

    int numberOfProcess;
    cout << "Input number of process: ";
    cin >> numberOfProcess;

    HANDLE hSemaphore = CreateSemaphore(NULL, 2, 2, L"Semaphore");
    HANDLE hMutex = CreateMutex(NULL, FALSE, L"Mutex");
    STARTUPINFO* Wstp = new STARTUPINFO[numberOfProcess];
    STARTUPINFO* Rstp = new STARTUPINFO[numberOfProcess];
    PROCESS_INFORMATION* Wpi = new PROCESS_INFORMATION[numberOfProcess];
    PROCESS_INFORMATION* Rpi = new PROCESS_INFORMATION[numberOfProcess];

    for (int i = 0; i < numberOfProcess; i++)
    {
        ZeroMemory(&Wstp[i], sizeof(STARTUPINFO));
        Wstp[i].cb = sizeof(STARTUPINFO);
        ZeroMemory(&Rstp[i], sizeof(STARTUPINFO));
        Rstp[i].cb = sizeof(STARTUPINFO);
        wstring writer = L"Writer.exe", reader = L"Reader.exe";
        CreateProcess(NULL, &writer[0], NULL, NULL, FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &Wstp[i], &Wpi[i]);
        CreateProcess(NULL, &reader[0], NULL, NULL, FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &Rstp[i], &Rpi[i]);
    }

    int counterW = 1;
    int counterR = 1;
    string line;
    HANDLE mesEvAdm[2];
    for (int i = 0; i < 2; i++) { 
        mesEvAdm[i] = OpenEvent(EVENT_MODIFY_STATE, FALSE, ReaderMes[i]); 
    }
    for (int i = 0; i < numberOfProcess; i++)
    {
        while (true)
        {
            DWORD mes = WaitForMultipleObjects(2, Writer, FALSE, INFINITE);
            wcout << WriterMes[mes] << endl;
            if (DWORD mes1 = WaitForMultipleObjects(2, End, FALSE, INFINITE)) {
                if (mes1 == WAIT_OBJECT_0)
                {
                    cout << "Writer " << counterW << " ended work" << endl;
                    counterW += 1;
                    break;
                }
            }

            cout << "Enter C or D: " << endl;
            cin >> line;
            line = "r" + line;
            wstring wline(line.begin(), line.end());
            if (wline._Equal(ReaderMes[0])) {

                SetEvent(mesEvAdm[0]);
                Sleep(5);
                ResetEvent(mesEvAdm[0]);
            }
            if (wline._Equal(ReaderMes[1])) {
                SetEvent(mesEvAdm[1]);
                Sleep(5);
                ResetEvent(mesEvAdm[1]);
            }

            DWORD mes2 = WaitForMultipleObjects(2, End, FALSE, INFINITE);
            if (mes2 == WAIT_OBJECT_0)
            {
                cout << "Reader " << counterR << " ended work" << endl;
                counterR += 1;
                break;
            }
            /*if (mes1 == WAIT_OBJECT_0 + 1)
            {
                cout << "Writer " << i + 1 << " ended work" << endl;
            }*/
        }
    }

    for (int i = 0; i < 2; i++)
    {
        CloseHandle(Writer[i]);
        CloseHandle(Reader[i]);
    }

    for (int i = 0; i < 2; i++) { 
        CloseHandle(End[i]); 
    }

    for (int i = 0; i < numberOfProcess; i++)
    {
        CloseHandle(Wpi[i].hThread);
        CloseHandle(Wpi[i].hProcess);
        CloseHandle(Rpi[i].hThread);
        CloseHandle(Rpi[i].hProcess);
    }

    CloseHandle(hSemaphore);
    return 0;
}