package aihw9;
public class Ice {
	public static void main(String[] args) {
		int flag = Integer.valueOf(args[0]);
		//let y be Ice days in that year
		int [] y = {118,151,121,96,110,117,132,104,125,118,125,123,110,127,131,99,126,144,136,126,
				91,130,62,112,99,161,78,124,119,124,128,131,113,88,75,111,97,112,101,101,91,110,100,
				130,111,107,105,89,126,108,97,94,83,106,98,101,108,99,88,115,102,116,115,82,110,81,96,
				125,104,105,124,103,106,96,107,98,65,115,91,94,101,121,105,97,105,96,82,116,114,92,98,
				101,104,96,109,122,114,81,85,92,114,111,95,126,105,108,117,112,113,120,65,98,91,108,
				113,110,105,97,105,107,88,115,123,118,99,93,96,54,111,85,107,89,87,97,93,88,99,108,94,
				74,119,102,47,82,53,115,21,89,80,101,95,66,106,97,87,109,57,87,117,91,62,65};
		//Let x be the year
		int [] x = new int[162];
		int year = 1855;		
		for (int i = 0; i < 162; i++) {
			x[i] = x[i] + year;
			year++;
		}
		
		//Printing out the data set
		if(flag == 100) {
			for (int i = 0; i < x.length; i++){
				System.out.println(x[i] + " " +y[i]);
			}
		}
		//Printing out: Number of Data point 
		//sample mean and sample standard deviation
		else if(flag == 200) {
			System.out.println(y.length);
			double mean = 0.0;
			double sd = 0.0;
			//Calculate sample mean
			for (int i = 0; i < y.length; i++){
				mean = mean + y[i];
			}
			mean = (mean / y.length);
			System.out.println(String.format("%.2f",mean));
			//Calculate sample standard deviation
			for (int i = 0; i < y.length; i++) {
				sd = sd + Math.pow((y[i] - mean), 2);
			}
			sd = sd / (y.length - 1);
			sd = Math.sqrt(sd);
			System.out.println(String.format("%.2f",sd));
		}
		//Performance of linear regression****************
		//Compute Mean Square Error
		else if(flag == 300) {
			double b0 = Double.valueOf(args[1]);
			double b1 = Double.valueOf(args[2]);
			double mse = 0.0;
			for (int i = 0; i < x.length; i++){
				mse = mse + (Math.pow((b0 + b1*x[i] - y[i]),2));
			}
			mse = mse / x.length;
			System.out.println(String.format("%.2f",mse));
		}
		//Performing gradient descent on MSE
		else if(flag == 400) {
			double b0 = Double.valueOf(args[1]);
			double b1 = Double.valueOf(args[2]);
			double GradMse = 0.0;
			double GradMse2 = 0.0;
			for (int i = 0; i < x.length; i++){
				GradMse = GradMse + (b0 + b1*x[i] - y[i]);
				GradMse2 = GradMse2 + ((b0 + b1*x[i] - y[i])*x[i]);
			}
			GradMse = (2*GradMse) / x.length;
			GradMse2 = (2*GradMse2) / x.length;
			System.out.println(String.format("%.2f",GradMse) +"\n" + String.format("%.2f",GradMse2));
		}
		//iteration of gradient descent on MSE
		else if(flag == 500) {
			double n = Double.valueOf(args[1]);
			double T = Double.valueOf(args[2]);
			double b0 = 0.0;
			double b1 = 0.0;
			for (int j = 1; j <= T; j++){
				//print iteration number
				System.out.print(j + " ");
				//Compute Gradient descent on MSE
				double GradMse = 0.0;
				double GradMse2 = 0.0;
				for (int i = 0; i < x.length; i++){
					GradMse = GradMse + (b0 + b1*x[i] - y[i]);
					GradMse2 = GradMse2 + ((b0 + b1*x[i] - y[i])*x[i]);
				}
				GradMse = (2*GradMse) / x.length;
				GradMse2 = (2*GradMse2) / x.length;	
				b0 = b0 - n*(GradMse);
				b1 = b1 - n*(GradMse2);
				System.out.print(String.format("%.2f",b0) +" " + String.format("%.2f",b1) + " ");
				//Compute MSE
				double mse = 0.0;
				for (int i = 0; i < x.length; i++){
					mse = mse + (Math.pow((b0 + b1*x[i] - y[i]),2));
				}
				mse = mse / x.length;
				System.out.println(String.format("%.2f",mse));
			}
		}
		else if(flag == 600 || flag == 700) {
			//Variables
			double meanX = 0.0;
			double meanY = 0.0;
			double b0 = 0.0;
			double b1 = 0.0;
			double denominator = 0.0;
			double year1 = Double.valueOf(args[1]);
			//Get the means 
			for (int i = 0; i < y.length; i++){
				meanY = meanY + y[i];
				meanX = meanX + x[i];
			}
			meanY = meanY / y.length;
			meanX = meanX / x.length;
			//Compute Closed form solution
			for (int i= 0; i < x.length; i++){
				b1 = b1 + ((x[i] - meanX)*(y[i] - meanY));
				denominator = denominator + Math.pow(((x[i] - meanX)),2);
			}
			b1 = b1 / denominator;
			b0 = meanY - b1*meanX;
			//Compute MSE
			double mse = 0.0;
			for (int i = 0; i < x.length; i++){
				mse = mse + (Math.pow((b0 + b1*x[i] - y[i]),2));
			}
			mse = mse / x.length;
			//Print for 600
			if(flag == 600) {
				System.out.println(String.format("%.2f",b0) + " " + String.format("%.2f",b1) + " " + String.format("%.2f",mse));
			}
			//Predict using model
			else if (flag == 700) {
				System.out.println(String.format("%.2f",((b0) + year1*b1)));
			}
		}
		else if(flag == 800 || flag == 900) {
			//Variables
			double mean = 0.0;
			double sd = 0.0;
			double n = Double.valueOf(args[1]);
			double T = Double.valueOf(args[2]);
			double b0 = 0.0;
			double b1 = 0.0;
			double [] newx = new double[x.length];
			//get mean
			for (int i = 0; i < x.length; i++) {
				mean = mean + x[i];
			}
			mean = mean / x.length;
			//Get standard deviation
			for (int i = 0; i < y.length; i++) {
				sd = sd + Math.pow((x[i] - mean), 2);
			}
			sd = sd / (x.length - 1);
			sd = Math.sqrt(sd);
			//compute new x
			for (int i = 0; i < x.length; i++){
				newx[i] = (x[i] - mean) /sd;
			}
			if (flag == 800) {
				//Gradient descent with x normalization
				for (int j = 1; j <= T; j++){
					//print iteration number
					System.out.print(j + " ");
					//Compute Gradient descent on MSE
					double GradMse = 0.0;
					double GradMse2 = 0.0;
					for (int i = 0; i < x.length; i++){
						GradMse = GradMse + (b0 + b1*newx[i] - y[i]);
						GradMse2 = GradMse2 + ((b0 + b1*newx[i] - y[i])*newx[i]);
					}
					GradMse = (2*GradMse) / x.length;
					GradMse2 = (2*GradMse2) / x.length;	
					b0 = b0 - n*(GradMse);
					b1 = b1 - n*(GradMse2);
					System.out.print(String.format("%.2f",b0) +" " + String.format("%.2f",b1) + " ");
					//Compute MSE
					double mse = 0.0;
					for (int i = 0; i < x.length; i++){
						mse = mse + (Math.pow((b0 + b1*newx[i] - y[i]),2));
					}
					mse = mse / x.length;
					System.out.println(String.format("%.2f",mse));
				}
			}
			else if (flag == 900){
				//Stochastic Gradient Descent with x normalization
				for (int j = 1; j <= T ; j++){
					//Get a random index at t = j
					int rand = 0 + (int)(Math.random() * x.length);
					b0 = b0 - n*(2*(b0 + b1*newx[rand] - y[rand]));
					b1 = b1 - n*(2*(b0 + b1*newx[rand] - y[rand])*newx[rand]);
					System.out.print(String.format("%.2f",b0) +" " + String.format("%.2f",b1) + " ");
					//Compute MSE
					double mse = 0.0;
					for (int i = 0; i < x.length; i++){
						mse = mse + (Math.pow((b0 + b1*newx[i] - y[i]),2));
					}
					mse = mse / x.length;
					System.out.println(String.format("%.2f",mse));					
				}
			}
		}	
	}
}
