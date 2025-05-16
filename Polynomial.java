public class Polynomial 
{
	// field 
	double [] coefficients;
	
	//  no-argument constructor
	public Polynomial() 
	{
        this.coefficients = new double[]{0};
    }
	
	// 1st constructor
	public Polynomial(double[] coefficients)
	{
		this.coefficients = coefficients;
        
    }
	// Add Function
	public Polynomial add(Polynomial other)
	{
	    int len1 = this.coefficients.length;
	    int len2 = other.coefficients.length;
	    int n = (len1 >= len2) ? len1 : len2;

	    
	    double[] result = new double[n];
	    
	    for (int i = 0; i < len1; i++) 
	    {
	        result[i] = this.coefficients[i];
	    }
	    

	   
	    for (int i = 0; i < len2; i++) 
	    {
	        result[i] += other.coefficients[i];
	    }

	    return new Polynomial(result); // invoking the 1st constructor
	}
	
	// Evaluate Function
	public double evaluate(double x)
	{
		double[] array = new double[this.coefficients.length]; 
		double sum = 0.0f;
		for (int i = 0; i < this.coefficients.length ; i++) 
	    {
	        array[i] = this.coefficients[i];
	        sum += (Math.pow(x, i)*array[i]);
	    }
		
		return sum;
		
		
	}
	
	// Has root Function
	public boolean hasRoot(double x)
	{
		if(evaluate(x) == 0)
			return true;
		return false;
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	