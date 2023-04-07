package bot.config;

public class Mathematica {
    public double doWork(String [] args){
        switch (args[0]){
            case "v1":{
                return first(args);
            }
            case "v2":{
                return second(args);
            }
            case "v3":{
                return third(args);
            }
            case "v4":{
                return fourth(args);
            }
            case "v5":{
                return fifth(args);
            }
            case "v6":{
                return six(args);
            }
            case "v7":{
                return seventh(args);
            }
        }
        return 7.777;
    }

    public double seventh(String[] args) {
        double x = Double.parseDouble(args[1]);


        double answer = 0.25*((1+x*x)/(1-x)+0.5*Math.tan(x));
        return answer;
    }

    public double six(String[] args) {
        double e = Double.parseDouble(args[1]);
        double x = Double.parseDouble(args[2]);


        double answer = Math.pow(e,(2*Math.sin(4*x)+Math.pow(Math.cos(Math.pow(x,2)),2))/(3*x));
        return answer;
    }

    public double fifth(String[] args) {
        double a = Double.parseDouble(args[1]);
        double b = Double.parseDouble(args[2]);
        double c = Double.parseDouble(args[3]);
        double d = Double.parseDouble(args[4]);
        double e = Double.parseDouble(args[5]);
        double x = Double.parseDouble(args[6]);

        double answer = Math.pow(Math.sqrt((a+b)*(a+b) / (c+d)),5) + Math.pow(e,Math.sqrt(x+1));
        return answer;
    }

    public double fourth(String[] args) {
        double a = Double.parseDouble(args[1]);
        double x = Double.parseDouble(args[2]);
        double Pi = Math.PI;


        double answer = Math.log10(Math.abs(Math.pow(a,7)))+
                Math.atan(Math.pow(x,2))+Pi/Math.sqrt(Math.abs(a+x));
        return answer;
    }

    public double third(String[] args) {
        double a0 = Double.parseDouble(args[1]);
        double a1 = Double.parseDouble(args[2]);
        double a2 = Double.parseDouble(args[3]);
        double x = Double.parseDouble(args[4]);

        double answer = Math.sqrt(a0+a1*x+a2*Math.pow(Math.sqrt(Math.abs(Math.sin(x))),3));
        return answer;
    }

    public double second(String[] args) {
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double a = Double.parseDouble(args[3]);
        double e = Double.parseDouble(args[4]);
        double w = Double.parseDouble(args[5]);

        double answer = (Math.abs(x-y))/(Math.pow(1+2*x,a)) - Math.pow(e,Math.sqrt(1+w));
        return answer;
    }

    public double first(String[] args){
        double n = Double.parseDouble(args[1]);
        double x = Double.parseDouble(args[2]);
        double a = Double.parseDouble(args[3]);
        double b = Double.parseDouble(args[4]);
        double c = Double.parseDouble(args[5]);

        double answer = ((5.0 * Math.pow(a,n*x))/(b+c))-Math.sqrt(Math.abs(Math.pow(Math.cos(x),3)));
        return answer;
    }
}
