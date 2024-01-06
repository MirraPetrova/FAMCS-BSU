#include <iostream>
#include <windows.h>
#include <string>

int main()
{
    int size;
    std::cout << "Enter array size: ";
    std::cin >> size;
    if (size <= 0)
        std::cout << "Error: uncorrect data";
    else {
        long* arr = new long[size];
        std::cout << "Enter array elements: ";
        for (int i = 0; i < size; i++)
            std::cin >> arr[i];

        int X;
        std::cout << "Enter X(width): ";
        std::cin >> X;

        std::string cmdLine = "Child.exe ";
        cmdLine += std::to_string(size) + " ";
        for (int i = 0; i < size; i++)
            cmdLine += std::to_string(arr[i]) + " ";
        //cmdLine += std::to_string(X) + " ";

        int len = cmdLine.length();
        char* cmdLineChar = new char[len + 1];
        strcpy_s(cmdLineChar, len + 1, cmdLine.c_str()); //copying one line to another

        STARTUPINFO si;
        ZeroMemory(&si, sizeof(si));
        si.cb = sizeof(si);
        si.dwFlags = STARTF_USEPOSITION;
        si.dwX = X;

        PROCESS_INFORMATION pi;
        ZeroMemory(&pi, sizeof(pi));
        if (!CreateProcess(NULL, cmdLineChar, NULL, NULL, FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &si, &pi))
        {
            std::cout << "Error creating child process: " << GetLastError() << std::endl;
            return 1;
        }

        WaitForSingleObject(pi.hProcess, INFINITE);

        CloseHandle(pi.hProcess);
        CloseHandle(pi.hThread);

        delete[] cmdLineChar;
        delete[] arr;
    }

    return 0;
}
