#include <iostream>﻿
#include <windows.h>
#include <vector>
#include <process.h>

HANDLE hEvent1 = CreateEvent(NULL, FALSE, FALSE, NULL);
HANDLE hEvent2 = CreateEvent(NULL, FALSE, FALSE, NULL);
CRITICAL_SECTION cs;
int k;
int A;
int interval;
long long mult = 1;

DWORD WINAPI Thread2Actions(LPVOID param) {
	std::vector<double>& arr = *static_cast<std::vector<double>*>(param);
	std::cout << "Enter a time interval to rest after preparing one element in the array: ";
	std::cin >> interval;

	std::vector<double> rightside;
	std::vector<double> leftside;
	for (double number : arr) {
		if (number < A)
			rightside.push_back(number);
		else
			leftside.push_back(number);
	}
	arr.clear();

	arr.insert(arr.end(), leftside.begin(), leftside.end());
	arr.insert(arr.end(), rightside.begin(), rightside.end());

	std::cout << "Forming result array: ";
	for (int i = 0; i < arr.size(); i++) {
		std::cout << arr[i] << " ";
		Sleep(interval);
	}
	std::cout << std::endl;

	SetEvent(hEvent1);
	return 0;
}

DWORD WINAPI Thread3Actions(LPVOID param) {
	std::vector<double>& arr = *static_cast<std::vector<double>*>(param);
	EnterCriticalSection(&cs);

	WaitForSingleObject(hEvent2, INFINITE);
	for (size_t i = k; i < arr.size(); i++)
		mult *= arr[i];

	LeaveCriticalSection(&cs);
	return 0;
}

int main() {
	A = 0;
	k = 0;
	
	int size;
	std::cout << "Enter array size: ";
	std::cin >> size;
	if (size <= 0)
		std::cout << "Error: uncorrect data";
	else {
		std::vector<double> arr;
		std::cout << "Enter array elements: " << std::endl;
		for (int i = 0; i < size; i++) {
			double number;
			std::cin >> number;
			arr.push_back(number);
		}

		std::cout << std::endl;
		std::cout << "Array size: " << size << std::endl;
		std::cout << "Current array: " << std::endl;
		for (int i = 0; i < size; i++)
			std::cout << arr[i] << " ";
		std::cout << std::endl;

		InitializeCriticalSection(&cs);

		HANDLE hThread2 = CreateThread(NULL, 0, Thread2Actions, &arr, CREATE_SUSPENDED, NULL);
		if (hThread2 == NULL)
			return GetLastError();
		std::cout << std::endl;
		std::cout << "Enter A: ";
		std::cin >> A;
		std::cout << "Enter k: ";
		std::cin >> k;
		if (k >= size)
			std::cout << "Error: this position does not exist";
		else {
			std::cout << std::endl;
			ResumeThread(hThread2);

			HANDLE hThread3 = CreateThread(NULL, 0, Thread3Actions, &arr, 0, NULL);
			if (hThread3 == NULL)
				return GetLastError();
			WaitForSingleObject(hEvent1, INFINITE);
			std::cout << std::endl;
			std::cout << "Result array: ";
			for (int i = 0; i < size; i++)
				std::cout << arr[i] << " ";
			std::cout << std::endl;

			SetEvent(hEvent2);

			EnterCriticalSection(&cs);
			std::cout << "Working result of thread MultElement: " << mult << std::endl;
			LeaveCriticalSection(&cs);

			WaitForSingleObject(hThread3, INFINITE);

			CloseHandle(hThread3);
			CloseHandle(hThread2);
			DeleteCriticalSection(&cs);
			CloseHandle(hEvent1);
			CloseHandle(hEvent2);
		}
	}

	return 0;
}