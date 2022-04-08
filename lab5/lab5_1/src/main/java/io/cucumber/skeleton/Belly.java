package io.cucumber.skeleton;

public class Belly {
    public void eat(int cukes) {
        System.out.println("nom nom nom, I ate " + cukes + "cukes");
    }

    public void wait(int hours) {
        System.out.println("waiting " + hours + " hours...");
    }

    public void growl() {
        System.out.println("*growl*");
    }
}
