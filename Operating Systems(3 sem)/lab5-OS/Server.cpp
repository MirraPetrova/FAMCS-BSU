#include <iostream>
#include <windows.h>
#include <string>

int main() {
	int size1, size2;
	std::cout << "-Server-\nEnter array1 size: ";
	std::cin >> size1;
	double* arr1 = new double[size1];
	std::cout << "Enter array1 elements: ";
	for (int i = 0; i < size1; i++) {
		std::cin >> arr1[i];
	}
	std::cout << std::endl;
	std::cout << "Enter array2 size: ";
	std::cin >> size2;
	double* arr2 = new double[size2];
	std::cout << "Enter array2 elements: ";
	for (int i = 0; i < size2; i++) {
		std::cin >> arr2[i];
	}
	HANDLE hEnableReadSearch = CreateEvent(NULL, FALSE, FALSE, L"EnableReadSearch");// creating an event to synchronize data exchange
	HANDLE hEnableReadServer = CreateEvent(NULL, FALSE, FALSE, L"EnableReadServer");// creating an event to synchronize data exchange
	if (hEnableReadSearch == NULL || hEnableReadServer == NULL) {
		std::cout << "Events were not created";
		return GetLastError();
	}

	HANDLE hWritePipe1, hReadPipe1, hWritePipe2, hReadPipe2, hInheritWritePmipe;
	SECURITY_ATTRIBUTES sa;
	sa.nLength = sizeof(SECURITY_ATTRIBUTES);// sets channel protection attributes
	sa.lpSecurityDescriptor = NULL;
	sa.bInheritHandle = TRUE;
	if (!CreatePipe(&hReadPipe1, &hWritePipe1, &sa, 0)) {
		std::cout << "-Server-\n Pipe1 was not created";
		return GetLastError();
	}
	if (!CreatePipe(&hReadPipe2, &hWritePipe2, &sa, 0)) {
		std::cout << "\n Pipe 2 was not created";
		return GetLastError();
	}

	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	char lpszComLine[80];
	wsprintf((LPWSTR)lpszComLine, L"%d %d", (int)hWritePipe2, (int)hReadPipe1);
	ZeroMemory(&si, sizeof(si));
	si.cb = sizeof(si);
	if (!CreateProcess(L"Search.exe", (LPWSTR)lpszComLine, NULL, NULL, TRUE, CREATE_NEW_CONSOLE, NULL, NULL, &si, &pi)) {
		std::cout << "\nSearch process is not created\n";
		return GetLastError();
	}
	CloseHandle(pi.hProcess);
	CloseHandle(pi.hThread);

	DWORD bytesWritten;
	if (!WriteFile(hWritePipe1, &size1, sizeof(int), &bytesWritten, NULL))
	{
		std::cout << "Array1 size write is failed " << std::endl;
		return GetLastError();
	}
	if (!WriteFile(hWritePipe1, arr1, sizeof(double) * size1, &bytesWritten, NULL))
	{
		std::cout << "Array1 elements write is failed " << std::endl;
		return GetLastError();
	}
	if (!WriteFile(hWritePipe1, &size2, sizeof(int), &bytesWritten, NULL))
	{
		std::cout << "Array2 size write is failed " << std::endl;
		return GetLastError();
	}
	if (!WriteFile(hWritePipe1, arr2, sizeof(double) * size2, &bytesWritten, NULL))
	{
		std::cout << "Array2 elements write is failed " << std::endl;
		return GetLastError();
	}
	SetEvent(hEnableReadSearch);// give a signal for the client to allow reading

	WaitForSingleObject(hEnableReadServer, INFINITE);

	DWORD byteRead;
	int resultSize;
	if (!ReadFile(hReadPipe2, &resultSize, sizeof(resultSize), &byteRead, NULL))
	{
		std::cout << "Size of result array read is failed " << std::endl;
		return GetLastError();
	}
	double* result = new double[resultSize];
	if (!ReadFile(hReadPipe2, result, sizeof(double) * resultSize, &byteRead, NULL))
	{
		std::cout << "Result array elements read is failed " << std::endl;
		return GetLastError();
	}

	std::cout << "\nResult array\n";
	for (int i = 0; i < resultSize; i++)
	{
		std::cout << result[i] << " ";
	}

	delete[] result;
	CloseHandle(hEnableReadSearch);
	CloseHandle(hEnableReadServer);
	CloseHandle(hWritePipe1);
	CloseHandle(hWritePipe2);
	CloseHandle(hReadPipe1);
	CloseHandle(hReadPipe2);
	delete[] arr2;
	delete[] arr1;
	return 0;
}
