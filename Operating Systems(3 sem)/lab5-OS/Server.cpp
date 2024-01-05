/*5.6. Написать программы двух консольных процессов Server и Searh, которые обмениваются сообщениями анонимным каналам (2 шт.): 1 процесс записывает в первый канал, читает из второго канала,  2 процесс записывает во второй канал, читает из первого канала .Создать наследуемые дескрипторы канала.

Одновременно сообщение может передаваться только одним из процессов.

Процесс- Server, который выполняет следующие действия:
- Размер массива1 и элементы массива1 вводятся с консоли. Тип массива: _ int16
- Размер массива2 и элементы массива2 вводятся с консоли. Тип массива: __int8
- Запускает процесс Searh.
- Передает процессу- Searh по анонимному каналу размеры массивов и элементы массивов. 
- Получает от процесса- Searh по анонимному каналу итоговый массив (пары индексов). Выводит полученную и переданную информацию на консоль. 


Процесс- Searh, который выполняет следующие действия.
- Получает каналу размеры массивов и элементы массивов по анонимному каналу от процесса-сервера.
- Выводит полученные данные на консоль.
- Определяет не совпадающие элементы из массивов, удалить дубли.
- Выводит выводит новый массив на консоль
- Передает  новый массив   по анонимному каналу процессу-серверу.
*/
#include <iostream>
#include <windows.h>
#include <string>
using namespace std;

int main() {
	int size1, size2;
	cout << "-Server-\nEnter array1 size: ";
	cin >> size1;
	__int16* arr1 = new __int16[size1];
	cout << "Enter array1 elements: ";
	for (int i = 0; i < size1; i++) {
		cin >> arr1[i];
	}
	cout << endl;
	cout << "Enter array2 size: ";
	cin >> size2;
	__int8* arr2 = new __int8[size2];
	cout << "Enter array2 elements: ";
	for (int i = 0; i < size2; i++) {
		cin >> arr2[i];
	}
	HANDLE hEnableReadSearch = CreateEvent(NULL, FALSE, FALSE, L"EnableReadSearch");// создаем событие для синхронизации обмена данными
	HANDLE hEnableReadServer = CreateEvent(NULL, FALSE, FALSE, L"EnableReadServer");// создаем событие для синхронизации обмена данными
	if (hEnableReadSearch == NULL || hEnableReadServer == NULL) {
		cout << "Events were not created";
		return GetLastError();
	}

	HANDLE hWritePipe1, hReadPipe1, hWritePipe2, hReadPipe2, hInheritWritePmipe;
	SECURITY_ATTRIBUTES sa;
	sa.nLength = sizeof(SECURITY_ATTRIBUTES);// устанавливает атрибуты защиты канала
	sa.lpSecurityDescriptor = NULL;
	sa.bInheritHandle = TRUE;
	if (!CreatePipe(&hReadPipe1, &hWritePipe1, &sa, 0)) {
		cout << "-Server-\n Pipe1 was not created";
		return GetLastError();
	}
	if (!CreatePipe(&hReadPipe2, &hWritePipe2, &sa, 0)) {
		cout << "\n Pipe 2 was not created";
		return GetLastError();
	}

	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	char lpszComLine[80];
	wsprintf((LPWSTR)lpszComLine, L"%d %d", (int)hWritePipe2, (int)hReadPipe1);
	ZeroMemory(&si, sizeof(si));
	si.cb = sizeof(si);
	if (!CreateProcess(L"Search.exe", (LPWSTR)lpszComLine, NULL, NULL, TRUE, CREATE_NEW_CONSOLE, NULL, NULL, &si, &pi)) {
		cout << "\nSearch process is not created\n";
		return GetLastError();
	}
	CloseHandle(pi.hProcess);
	CloseHandle(pi.hThread);

	DWORD bytesWritten;
	if (!WriteFile(hWritePipe1, &size1, sizeof(int), &bytesWritten, NULL))
	{
		cout << "Array1 size write is failed " << endl;
		return GetLastError();
	}
	if (!WriteFile(hWritePipe1, arr1, sizeof(__int16) * size1, &bytesWritten, NULL))
	{
		cout << "Array1 elements write is failed " << endl;
		return GetLastError();
	}
	if (!WriteFile(hWritePipe1, &size2, sizeof(int), &bytesWritten, NULL))
	{
		cout << "Array2 size write is failed " << endl;
		return GetLastError();
	}
	if (!WriteFile(hWritePipe1, arr2, sizeof(__int8) * size2, &bytesWritten, NULL))
	{
		cout << "Array2 elements write is failed " << endl;
		return GetLastError();
	}
	SetEvent(hEnableReadSearch);// даем сигнал на разрешение чтения клиентом

	WaitForSingleObject(hEnableReadServer, INFINITE);

	DWORD byteRead;
	int resultSize;
	if (!ReadFile(hReadPipe2, &resultSize, sizeof(resultSize), &byteRead, NULL))
	{
		cout << "Size of result array read is failed " << endl;
		return GetLastError();
	}
	double* result = new double[resultSize];
	if (!ReadFile(hReadPipe2, result, sizeof(double) * resultSize, &byteRead, NULL))
	{
		cout << "Result array elements read is failed " << endl;
		return GetLastError();
	}

	cout << "\nResult array\n";
	for (int i = 0; i < resultSize; i++)
	{
		cout << result[i] << " ";
	}

	CloseHandle(hEnableReadSearch);
	CloseHandle(hEnableReadServer);
	CloseHandle(hWritePipe1);
	CloseHandle(hWritePipe2);
	CloseHandle(hReadPipe1);
	CloseHandle(hReadPipe2);
	return 0;
}