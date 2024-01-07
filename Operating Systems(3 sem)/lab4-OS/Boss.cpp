#include <iostream>
#include <windows.h>
#include <string>

HANDLE eventA, eventB, eventC, eventD, EndParent, EndChild, hSemaphore;

STARTUPINFO ZeroStart()
{
	STARTUPINFO si;
	ZeroMemory(&si, sizeof(STARTUPINFO));
	si.cb = sizeof(STARTUPINFO);
	return si;
}
char* CreateCmdLine(std::string file, int param) {
	file = file + " " + std::to_string(param);
	char contain[255];
	strcpy_s(contain, file.c_str());
	return contain;
}

int main() {

	hSemaphore = CreateSemaphore(NULL, 2, 2, L"Semaphore");
	eventA = CreateEvent(NULL, FALSE, FALSE, L"eventA"); 
	if (eventA == NULL)
		return GetLastError();
	eventB = CreateEvent(NULL, FALSE, FALSE, L"eventB"); 
	if (eventB == NULL)
		return GetLastError();
	eventC = CreateEvent(NULL, FALSE, FALSE, L"eventC"); 
	if (eventC == NULL)
		return GetLastError();
	eventD = CreateEvent(NULL, FALSE, FALSE, L"eventD"); 
	if (eventD == NULL)
		return GetLastError();
	EndParent = CreateEvent(NULL, FALSE, FALSE, L"EndParent"); 
	if (EndParent == NULL)
		return GetLastError();
	EndChild = CreateEvent(NULL, FALSE, FALSE, L"EndChild"); 
	if (EndChild == NULL)
		return GetLastError();
	HANDLE hMutex;
	hMutex = CreateMutex(NULL, FALSE, L"Mutex"); 

	int numberOfParent;
	int numberOfChild;
	int numberOfParentMess;
	int numberOfChildMess;

	std::cout << "Enter amount of parent processes: ";
	std::cin >> numberOfParent;
	std::cout << "Enter amount of parent processes messages: ";
	std::cin >> numberOfParentMess;
	std::cout << "Enter amount of child processes: ";
	std::cin >> numberOfChild;
	std::cout << "Enter amount of child processes messages: ";
	std::cin >> numberOfChildMess;
	std::cout << std::endl;

	STARTUPINFO* InfoParent = new STARTUPINFO[numberOfParent];// Create arrays for storing process information
	PROCESS_INFORMATION* piParent = new PROCESS_INFORMATION[numberOfParent];
	HANDLE* hParent = new HANDLE[numberOfParent];

	STARTUPINFO* InfoChild = new STARTUPINFO[numberOfChild];
	PROCESS_INFORMATION* piChild = new PROCESS_INFORMATION[numberOfChild];
	HANDLE* hChild = new HANDLE[numberOfChild];

	for (int i = 0; i < numberOfParent; i++) {
		InfoParent[i] = ZeroStart();
		char* cmdLine = CreateCmdLine("Parent.exe", numberOfParentMess);
		if (!CreateProcess(NULL, (LPWSTR)cmdLine, NULL, NULL, FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &InfoParent[i], &piParent[i])) {
			std::cout << "Parent process was not created.\n";
			return 0;
		}
		hParent[i] = piParent[i].hProcess;
	}

	
	for (int i = 0; i < numberOfChild; i++) {
		InfoChild[i] = ZeroStart();
		char* cmdLine = CreateCmdLine("Child.exe", numberOfChildMess);
		if (!CreateProcess(NULL, (LPWSTR)cmdLine, NULL, NULL, FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &InfoChild[i], &piChild[i])) {
			std::cout << "Child process was not created.\n";
			return 0;
		}
		hChild[i] = piChild[i].hProcess;
	}

	int AmountMess = 0;
	while (true) {
		if (WaitForSingleObject(EndParent, 0) == WAIT_OBJECT_0 || WaitForSingleObject(EndChild, 0)) {//if any process ended
			if (WaitForSingleObject(eventA, 0) == WAIT_OBJECT_0) {
				AmountMess++;
				std::cout << "message A from Parent" << std::endl;
			}
			if (WaitForSingleObject(eventB, 0) == WAIT_OBJECT_0) {
				AmountMess++;
				std::cout << "message B from Parent" << std::endl;
			}
			if (WaitForSingleObject(eventC, 0) == WAIT_OBJECT_0) {
				AmountMess++;
				std::cout << "message C from Child" << std::endl;
			}
			if (WaitForSingleObject(eventD, 0) == WAIT_OBJECT_0) {
				AmountMess++;
				std::cout << "message D from Child" << std::endl;
			}
		}
		
		if (AmountMess >= numberOfParent * numberOfParentMess + numberOfChild * numberOfChildMess) {// if messages were handled
			break;
		}
	}

	CloseHandle(piChild);
	CloseHandle(piParent);
	CloseHandle(EndChild);
	CloseHandle(EndParent);
	CloseHandle(eventD);
	CloseHandle(eventC);
	CloseHandle(eventB);
	CloseHandle(eventA);
	CloseHandle(hSemaphore);

	return 0;
}
