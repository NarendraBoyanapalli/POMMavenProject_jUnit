package com.gmail.qa.base;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RetryRule implements TestRule
{
	private static class RepeatStatement extends Statement
	{
		private final Statement statement;
        private final int repeat;    

        public RepeatStatement(Statement statement, int repeat) 
        {
        	this.statement = statement;
            this.repeat = repeat;
        }
        
        //Rerun When Script Passes 
        @Override
		public void evaluate() throws Throwable 
		{
			for (int i=0;i<repeat;i++)
				statement.evaluate();
		}
		
        //Rerun When Script Fails
		/*@Override
		public void evaluate() throws Throwable 
		{
			Throwable caughtthrowable = null;
			
			for (int i=0;i<repeat;i++)
			{
				try
				{
					statement.evaluate();
					return;
				}
				catch(Throwable t)
				{
					caughtthrowable = t;
				}
			}
			throw caughtthrowable;
		}*/
	}
	
	@Override
	public Statement apply(Statement statement, Description description) 
	{
		Statement result = statement;
        Retry retry = description.getAnnotation(Retry.class);
        if (retry != null) 
        {
            int times = retry.value();
            result = new RepeatStatement(statement, times);
        }
        return result;
        }	

}
