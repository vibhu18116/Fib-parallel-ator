# Fib-parallel-ator
A Fibonacci number calculator based on parallel approach

Fib-parallel-ator is a producer-consumer based Fibonacci number calculator in Java by using explicit multithreading.
The program starts by asking the user about the total number of consumer threads to generate. 
This interaction happens on a producer thread that first creates all the consumer threads, and then endlessly loops 
asking the user to enter the number whose Fibonacci number is to be calculated. The producer thread does not
display the result for any input number provided by the user unless the user explicitly asks the producer thread to display 
the result of all pending queries. The producer terminates only when specified by the user.

Every time the user enters a number whose Fibonacci value is to be calculated, the producer pushes this number on a queue 
shared between the producer and the consumers. One of the consumer threads takes the number out from this queue and calculates
the Fibonacci result recursively, but sequentially, i.e., without using any parallelism. Once it has calculated the result, 
it pushes the result along with its computation time as one single task to another shared queue between the producer and the consumers.
