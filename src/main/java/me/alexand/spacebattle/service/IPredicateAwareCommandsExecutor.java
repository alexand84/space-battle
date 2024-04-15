package me.alexand.spacebattle.service;

public interface IPredicateAwareCommandsExecutor extends ICommandsExecutor {

    void updatePredicate(IPredicate predicate);

}
