package com.copyonwrite.statemachine;

import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wolf
 */
public class StateMachineTest {
    @Test
    public void itShouldBePossibleToRejectInitialStateAsValidEndState() {
        StateMachine<String, String> sm = new StateMachine<>("start");
        Assert.assertFalse(sm.accept());
    }
    
    @Test
    public void itShouldBePossibleToAcceptInitialStateAsValidEndState() {
        StateMachine<String, String> sm = new StateMachine<>("start");
        sm.addAcceptStates("start");
        Assert.assertTrue(sm.accept());
    }
    
    @Test
    public void itShouldBePossibleToAcceptOneValidTransitionToAnEndState() {
        StateMachine<String, String> sm = new StateMachine<>("start");
        sm.addTransition("start", "goToEnd", "end");
        sm.addAcceptStates("end");
        Assert.assertTrue(sm.accept("goToEnd"));
    }
    
    @Test
    public void itShouldBePossibleToRejectOneValidTransitionToAnIntermediateState() {
        StateMachine<String, String> sm = new StateMachine<>("start");
        sm.addTransition("start", "goToNext", "intermediate");
        sm.addTransition("intermediate", "goToNext", "end");
        sm.addAcceptStates("end");
        Assert.assertFalse(sm.accept("goToNext"));
    }
    
    @Test
    public void itShouldBePossibleToAcceptTwoValidTransitionsToAnEndState() {
        StateMachine<String, String> sm = new StateMachine<>("start");
        sm.addTransition("start", "goToNext", "intermediate");
        sm.addTransition("intermediate", "goToNext", "end");
        sm.addAcceptStates("end");
        Assert.assertTrue(sm.accept("goToNext", "goToNext"));
    }
    
    @Test
    public void itShouldBePossibleToRejectOneInvalidTransition() {
        StateMachine<String, String> sm = new StateMachine<>("start");
        sm.addTransition("start", "goToEnd", "end");
        sm.addAcceptStates("end");
        Assert.assertFalse(sm.accept("invalidTransition"));
    }
    
    @Test
    public void itShouldBePossibleToAcceptAThousandValidInputTransitions() {
        StateMachine<String, Integer> sm = new StateMachine<>("start");
        sm.addTransition("start", 33, "33");
        sm.addTransition("33", 33, "33");
        sm.addAcceptStates("33");
        List<Integer> transitions = new LinkedList<>();
        for(int i = 0; i < 1000; i++) {
            transitions.add(33);
        }
        Assert.assertTrue(sm.accept(transitions));
    }
    
    @Test
    public void itShouldBePossibeToRejectAThousandValidInputTransitionsWithOneFaultyTransitionAtTheEnd() {
        StateMachine<String, Integer> sm = new StateMachine<>("start");
        sm.addTransition("start", 33, "33");
        sm.addTransition("33", 33, "33");
        sm.addAcceptStates("33");
        List<Integer> transitions = new LinkedList<>();
        for(int i = 0; i < 1000; i++) {
            transitions.add(33);
        }
        transitions.add(34);
        Assert.assertFalse(sm.accept(transitions));
    }
}
