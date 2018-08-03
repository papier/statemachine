package com.copyonwrite.statemachine;

import java.util.*;

public class StateMachine <S, T> {
    private final Map<S, Map<T, S>> transitions;
    private final Set<S> acceptStates;
    private final S initialState;

    public StateMachine(S initialState) {
        this.transitions = new HashMap<>();
        this.acceptStates = new HashSet<>();

        this.initialState = initialState;
    }

    public void addTransition(S startState, T transition, S endState) {
        if (!this.transitions.containsKey(startState)) {
            this.transitions.put(startState, new HashMap<>());
        }
        // Ensure we do not override an already present transition
        if (transitions.get(startState).containsKey(transition)) {
            throw new IllegalArgumentException(String.format("Illegal override for transition %s in state %s.", transition, startState));
        }
        transitions.get(startState).put(transition, endState);
    }

    public void addAcceptStates(S... acceptStates) {
        for(S state : acceptStates) {
            this.acceptStates.add(state);    
        }
    }

    public boolean accept(T ...inputArray) {
        return StateMachine.this.accept(Arrays.asList(inputArray));
    }
    
    public boolean accept(List<T> inputList) {
        S currentState = this.initialState;
        for (T transition : inputList) {
            if (validTransition(currentState, transition)) {
                currentState = followState(currentState, transition);
            } else {
                return false;
            }
        }
        return this.acceptStates.contains(currentState);
    }

    private boolean validTransition(S currentState, T transition) {
        return this.transitions.containsKey(currentState) && this.transitions.get(currentState).containsKey(transition);
    }

    private S followState(S currentState, T transition) {
        return this.transitions.get(currentState).get(transition);
    }
}