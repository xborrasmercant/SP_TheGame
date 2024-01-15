package Controller;

import Model.LM;

public class TGPCT {
    private LM model;
    private LCT controller;

    public TGPCT() {
        model = new LM();
        controller = new LCT(this);
    }

    public static void main(String[] args) {
        TGPCT tgpct = new TGPCT();

    }
}
