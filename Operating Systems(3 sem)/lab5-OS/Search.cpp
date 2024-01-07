#include <iostream>
#include <windows.h>
#include <conio.h>
#include <set>
#include <vector>
#include <string>

int main(int argc, char* argv[]) {

	HANDLE hWritePipe = (HANDLE)atoi(argv[0]);
	HANDLE hReadPipe = (HANDLE)atoi(argv[1]);
	HANDLE hEnableReadServer = OpenEvent(EVENT_ALL_ACCESS, FALSE, L"EnableReadServer");// to synchronize data exchange
	HANDLE hEnableReadSearch = OpenEvent(EVENT_ALL_ACCESS, FALSE, L"EnableReadSearch");// to synchronize data exchange
	if (hEnableReadSearch == NULL || hEnableReadServer == NULL) {// opening an event that allows reading
		std::cout << "Events were not created";
		return GetLastError();
	}

	int size1, size2;
	DWORD bytesWritten;

	WaitForSingleObject(hEnableReadSearch, INFINITE);// waiting for permission to read

	DWORD byteRead;
	if (!ReadFile(hReadPipe, &size1, sizeof(size1), &byteRead, NULL))
	{
		std::cout << "Array1 size read is failed " << std::endl;
		return GetLastError();
	}
	double* arr1 = new double[size1];
	if (!ReadFile(hReadPipe, arr1, sizeof(double) * size1, &byteRead, NULL))
	{
		std::cout << "Array1 elements read is failed " << std::endl;
		return GetLastError();
	}
	if (!ReadFile(hReadPipe, &size2, sizeof(size2), &byteRead, NULL))
	{
		std::cout << "Array2 size read is failed " << std::endl;
		return GetLastError();
	}
	double* arr2 = new double[size2];
	if (!ReadFile(hReadPipe, arr2, sizeof(double) * size2, &byteRead, NULL))
	{
		std::cout << "Array2 elements read is failed " << std::endl;
		return GetLastError();
	}
	std::cout << "Received information from Server:" << std::endl;
	std::cout << "Size of array 1: " << size1 << std::endl;
	std::cout << "Elements of array 1: ";
	for (int i = 0; i < size1; i++)
	{
		std::cout << arr1[i] << " ";
	}
	std::cout << std::endl;
	std::cout << "Size of array 2: " << size2 << std::endl;
	std::cout << "Elements of array 2: ";
	for (int i = 0; i < size2; i++)
	{
		std::cout << arr2[i] << " ";
	}
	std::cout << std::endl;

	std::set<double> uniqueElements(arr1, arr1 + size1);
	std::vector<double> result;

	for (int i = 0; i < size2; i++) {
		if (uniqueElements.count(arr2[i]) > 0) {
			uniqueElements.erase(arr2[i]);
		}
		else {
			result.push_back(arr2[i]);
		}
	}
	if (!uniqueElements.empty()) {
		for (auto un : uniqueElements)
			result.push_back(un);
	}
	
	std::cout << "Result array\n";
	for (int i = 0; i < result.size(); i++)
	{
		std::cout << result[i] << " ";
	}

	int resultSize = result.size();
	if (!WriteFile(hWritePipe, &resultSize, sizeof(resultSize), &bytesWritten, NULL))
	{
		std::cout << "Result array size write is failed " << std::endl;
		return GetLastError();
	}
	if (!WriteFile(hWritePipe, result.data(), resultSize * sizeof(double), &bytesWritten, NULL))
	{
		std::cout << "Result array elements write is failed " << std::endl;
		return GetLastError();
	}
	SetEvent(hEnableReadServer);

	std::cout << "\nTo exit press any key ";
	_getch();
	
	delete[] arr2;
	delete[] arr1;
	CloseHandle(hEnableReadSearch);
	CloseHandle(hEnableReadServer);
	CloseHandle(hWritePipe);
	CloseHandle(hReadPipe);
	return 0;
}
