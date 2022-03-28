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

![](RackMultipart20220328-4-16hgwsb_html_655aeb139fbb31d7.png)

**Closed:** It is the case that the switch is closed. In this case, the requests to the service are transmitted successfully up to the error rate at the threshold value. The Breaker keeps a counter for the failed requests and when this counter exceeds the threshold value, the Breaker becomes half-open. income.

**Open:** It is the case when the switch is open. In this case, the requests to the service become fail before they are sent to the service, and an error message is returned as a response.

**Half-Open:** In case the switch is half-open. In this case, the requests to the service are sent to the service and a response is expected. If the response is successful, the previous errors are considered to be corrected, the switch is closed and the counter is reset.

If one of the sent errors fails again, the Switch is turned on and the Timeout continues until the error is resolved.

Let's examine Resilience4j through an example.

- Have Two Services

![](RackMultipart20220328-4-16hgwsb_html_6f21e372fade41a4.png) ![](RackMultipart20220328-4-16hgwsb_html_6580fbec17c2a14d.png)

- Let's assume that our Second Service is not active in the first place; In this case, we receive the following responses because our services cannot communicate.

![](RackMultipart20220328-4-16hgwsb_html_2d13c9a7c22799b0.png) ![](RackMultipart20220328-4-16hgwsb_html_ce9eabd9e1b66015.png)

- failedCalls value is our Counter and we have restricted 5 requests in properties.

At the end of 5 requests;

![](RackMultipart20220328-4-16hgwsb_html_33ea42535cfdebe8.png)

Our service has become HALF\_OPEN, in this case we are checking our service again.

- After the HALF\_OPEN status, the failedCalls value is our Counter and we have restricted 3 requests in properties.

after 3 requests

![](RackMultipart20220328-4-16hgwsb_html_57450644cad183b4.png)

- When our service becomes OPEN, we have stopped all requests and prevented memory overload.

- When we activate our second service

![](RackMultipart20220328-4-16hgwsb_html_c72197d3a7dc7a6a.png)

![](RackMultipart20220328-4-16hgwsb_html_b1f1614dd7bc25f9.png)

As in the figure, we expect the failedCalls Counter to remain constant at zero and the state remains in the CLOSE position.
