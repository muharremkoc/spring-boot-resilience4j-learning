# spring-boot-resilience4j-learning

# **What is Resilience4j?**

Circuit Breaker is the circuit element used to prevent this situation when there is an overload in the system.

**So how is this Circuit Breaker used in software?**

If we go through the example;

"Let's have two services and these services are services that send requests to each other.

A down occurs in one of the services. This down process affects its controls-\>controller affects the other service-\>service affects its own controls. A series of errors occur this way." If this error occurs constantly, it can cause serious problems by constantly affecting the resource consumption.

That's where Circuit Breaker comes into play,

If an error occurs above a specified threshold, it returns an error message without forwarding the request to the other service, preventing the system from being overloaded.(open\_state)

Controls the operation by forwarding some of the requests coming after a certain threshold value to the other service (half\_open\_state);

If an error occurs, it closes the system again and prevents transitions, if no error is received, it allows all requests to pass (closed\_state).

In this way, Circuit Breaker protects the system order and prevents overloading.

![image](https://user-images.githubusercontent.com/80245013/160360687-169db442-c707-4807-89e6-8d54c47e71ab.png)

**Closed:** It is the case that the switch is closed. In this case, the requests to the service are transmitted successfully up to the error rate at the threshold value. The Breaker keeps a counter for the failed requests and when this counter exceeds the threshold value, the Breaker becomes half-open. income.

**Open:** It is the case when the switch is open. In this case, the requests to the service become fail before they are sent to the service, and an error message is returned as a response.

**Half-Open:** In case the switch is half-open. In this case, the requests to the service are sent to the service and a response is expected. If the response is successful, the previous errors are considered to be corrected, the switch is closed and the counter is reset.

If one of the sent errors fails again, the Switch is turned on and the Timeout continues until the error is resolved.

Let's examine Resilience4j through an example.

- Have Two Services(This Pictures are example pictures)

![image](https://user-images.githubusercontent.com/80245013/160360737-1bf8c49a-42ed-4934-a3e5-357386da6d76.png) 

![image](https://user-images.githubusercontent.com/80245013/160360804-9e0377f4-bc2f-4408-97d3-4522ba1a29ac.png)

- Let's assume that our Second Service is not active in the first place; In this case, we receive the following responses because our services cannot communicate.

![image](https://user-images.githubusercontent.com/80245013/160360937-d2457486-2eb4-4aee-8ba1-02288897bebf.png) 

![image](https://user-images.githubusercontent.com/80245013/160360967-ed923206-ca6e-4047-a426-77a553b7a219.png)

- failedCalls value is our Counter and we have restricted 5 requests in properties.

At the end of 5 requests;

![image](https://user-images.githubusercontent.com/80245013/160361005-609e169f-e71d-44e7-8209-6995551899c2.png)

Our service has become HALF\_OPEN, in this case we are checking our service again.

- After the HALF\_OPEN status, the failedCalls value is our Counter and we have restricted 3 requests in properties.

after 3 requests

![image](https://user-images.githubusercontent.com/80245013/160361035-93fb9c10-a08f-4a99-a83e-de30a01dfd73.png)

- When our service becomes OPEN, we have stopped all requests and prevented memory overload.

- When we activate our second service

![image](https://user-images.githubusercontent.com/80245013/160361068-61204c45-099e-419f-a97e-0b914ae504f5.png)

![image](https://user-images.githubusercontent.com/80245013/160361095-80310b59-773a-4f9b-8ffa-7e2d5a1409c5.png)

As in the figure, we expect the failedCalls Counter to remain constant at zero and the state remains in the CLOSE position.
