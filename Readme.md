# StateMachine

A simple implementation of a generic deterministic state machine in Java.

The implementation uses nested maps to map the transitions from one state to another.

Obviously this is a very slow way to implement a state machine but I appreciate the idea and the low line count for this solution.

It can be easily extended to trigger arbitrary actions before entering/leaving states.

Another extension idea is to keep an internal current state and do transitions step wise instead of providing all transitions to the accept methods.