#include <iostream>
#include <windows.h>
#include <time.h>
#include <string>

HANDLE hSemaphore, eventC, eventD, EndChild;
CRITICAL_SECTION csA;

int main(int argc, char* argv[]) {

	hSemaphore = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, L"Semaphore");
	if (hSemaphore == NULL) {
		std::cout << "Create semaphore failed." << std::endl;
		return GetLastError();
	}
	eventC = CreateEvent(NULL, FALSE, FALSE, L"eventC");
	if (eventC == NULL)
		return GetLastError();
	eventD = CreateEvent(NULL, FALSE, FALSE, L"eventD");
	if (eventD == NULL)
		return GetLastError();
	EndChild = CreateEvent(NULL, FALSE, FALSE, L"EndChild");
	if (EndChild == NULL)
		return GetLastError();

	int AmountMess = atoi(argv[argc - 1]);//copy the last command line argument in integer

	WaitForSingleObject(hSemaphore, INFINITE);//wait for the semaphore to become signaled

	for (int i = 0; i < AmountMess; i++) {
		std::cout << "Enter the message(C or D): ";
		char mess;
		std::cin >> mess;
		std::cout << std::endl;

		if (mess == 'C')
			SetEvent(eventC);
		if (mess == 'D')
			SetEvent(eventD);

		SetEvent(EndChild);//signal of ending
	}

	ReleaseSemaphore(hSemaphore, 1, NULL);

	CloseHandle(EndChild);
	CloseHandle(eventC);
	CloseHandle(eventD);

	return 0;
}
