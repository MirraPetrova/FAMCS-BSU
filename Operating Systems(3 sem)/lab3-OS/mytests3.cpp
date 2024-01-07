#include "CppUnitTest.h"
#include <iostream>
#include <vector>
#include "../lab3(var 5)/lab3.cpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

void SetK(size_t mean) {
    k = mean;
}

namespace UnitTestLab3
{
    TEST_CLASS(UnitTestLab3)
    {
    public:

        TEST_METHOD(TestThread2)
        {
            std::vector<double> testarr = { 3.0, 5.0, 1.0, 7.0 };
            int interval = 0;
            std::stringstream input;
            input << interval << "\n";
            std::cin.rdbuf(input.rdbuf());//The std::cin and std::cout streams are redirected to the input and output streams

            std::stringstream output;
            std::cout.rdbuf(output.rdbuf());

            std::cin.rdbuf(std::cin.rdbuf());
            std::cout.rdbuf(std::cout.rdbuf());

            Assert::AreEqual(3.0, testarr[0]);
            Assert::AreEqual(5.0, testarr[1]);
            Assert::AreEqual(1.0, testarr[2]);
            Assert::AreEqual(7.0, testarr[3]);
        }

        TEST_METHOD_INITIALIZE(SetUp) {
            InitializeCriticalSection(&cs);
        }

        TEST_METHOD_CLEANUP(TearDown) {
            DeleteCriticalSection(&cs);
        }

        TEST_METHOD(Thread3Actions_CheckCalculating) {

            mult = 1;
            std::vector<double> testarr = { 3.0, 5.0, 2.0 };
            k = 0;

            DWORD threadId;
            HANDLE hThread = CreateThread(NULL, 0, Thread3Actions, &testarr, 0, &threadId);
            SetEvent(hEvent2);

            WaitForSingleObject(hThread, INFINITE);
            CloseHandle(hThread);

            Assert::AreEqual(3.0 * 5.0 * 2.0, (double)mult);
        }

        TEST_METHOD(FromPositionK) {
            mult = 1;
            std::vector<double> testarr = { 3.0, 5.0, 2.0, 4.0 };
            k = 1;

            DWORD threadId;
            HANDLE hThread = CreateThread(NULL, 0, Thread3Actions, &testarr, 0, &threadId);
            SetEvent(hEvent2);

            WaitForSingleObject(hThread, INFINITE);
            CloseHandle(hThread);

            Assert::AreEqual(5.0 * 2.0 * 4.0, (double)mult);
        }
    };
}