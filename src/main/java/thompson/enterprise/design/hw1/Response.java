package thompson.enterprise.design.hw1;

public class Response {

    public String respondToGreeting(Greeting greeting){
        if(greeting.equals(Greeting.HELLO)){
            return "Hi";
        } else if(greeting.equals(Greeting.GREETING)){
            return "Salutations";
        } else{
            return "Sorry, I didn't hear you";
        }
    }

    public String respondToHowAreYou(Feeling feeling){
        switch (feeling){
            case FINE: return "I'm glad to hear that";
            case TIRED: return "Maybe you should take a vacation";
            default: return "I'm not familiar with that condition. Is it serious?";
        }
    }

    public String respondToHowIsTheWeather(int temp, SkyCondition skyCondition){
        switch(skyCondition){
            case SUNNY:
                return temp > 70 ? "It's beautiful out today - warm and sunny" :
                        "It's sunny out, but a little cooler than I'd like";
            case RAINY:
                return temp > 70 ? "It's rainy, but at least it's warm out!" :
                        "It's rainy and chilly, a good day to stay inside";
            default:
                return "Not sure about this weather";
        }
    }

    public String respondHowIsYourSpouse(Person person){
        return "";
    }
}
