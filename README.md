# IoT Power Consumption Estimator

A service to estimate the power consumption of an IoT device. The device being monitored is assumed to consume 10W when fully utilised and has a linear behaviour.  

### Design
This solution has a **modular** implementation in mind to allow for more **granular tests**. In addition, it solves the problems of unordered messages by using an **sorted data structure** which amortises the cost of sorting as the message set grows. This solution contains the most basic validation but is designed for future flexibility based on firm foundations.

### Background

The service receives a stream of messages from an IoT device. Each message contains a unix timestamp and a message. There are three message types:
- `Ping`: a message stream is about to follow
- `Delta`: contains a decimal number between 0 and 1 which corresponds to the energy level adjustment
- `Pong`: a message stream has finished

### Limitations

Since we assume unreliable wireless access, messages can be duplicated and received out of order. The solution needs to handle cases like that.

### Examples

```bash
> 10000 Ping
> 10001 Delta +0.5
> 13601 Pong
Estimation: 5.0 Wh
```

```bash
> 100002 Ping
> 100003 Delta +0.5
> 103603 Delta -0.25
> 105403 Delta +0.75
> 105403 Delta +0.75
> 107203 Pong
Estimation: 11.25 Wh
```
