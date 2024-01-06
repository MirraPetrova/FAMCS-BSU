#include "CppUnitTest.h"
#include <windows.h>
#include <string>
#include "fstream"
#include "vector"
#include <ctime>

double CalcOperations(int NumbOperation, std::vector<double> Numbers);
void InputsThreads(int k);
int main(int argc, char* argv[]);

using namespace Microsoft::VisualStudio::CppUnitTestFramework;

namespace Tests {
	TEST_CLASS(CalcOperatonsTest) {
public:

	TEST_METHOD(Test1) {
		int NumbOperation = 1;
		std::vector <double> Numbers = { 2.7, 4.8, 9.6, 38.8, 0.6, 0.1, 8.0, 5.9, 2, 5, 7.2, 5.3};// 12 numbs
		Assert::IsTrue(abs(CalcOperations(NumbOperation, Numbers) - 90) < 1e-6);;
	}
	TEST_METHOD(Test2) {
		int NumbOperation = 2;
		std::vector <double> Numbers = { 0.5, 8, 4.6, 0.7, 1.8, 5.3, 3, 6.9, 1, 0.1, 3.0, 0};
		Assert::IsTrue(abs(CalcOperations(NumbOperation, Numbers) - 0) < 1e-6);
	}
	TEST_METHOD(Test3) {
		int NumbOperation = 3;
		std::vector <double> Numbers = { 0.7, 2.8, 1, 4.9, 2.0, 3, 0.1, 2.9, 0, 3.6, 1.1, 2};
		Assert::IsTrue(abs(calculate(option, v) - 72.93) < 1e-6);
	}
	};
}