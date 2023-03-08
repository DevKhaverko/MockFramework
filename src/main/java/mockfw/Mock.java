package mockfw;

import java.util.ArrayList;

public class Mock {
    private MockState state;

    private ArrayList<Variant> variants;
    public MockState getState() {
        return state;
    }

    public void setState(MockState state) {
        this.state = state;
    }

    public ArrayList<Variant> getVariants() {
        return variants;
    }

    public void addNewVariant(Variant newVariant) {
        variants.add(newVariant);
    }
    public void setVariants(ArrayList<Variant> variants) {
        this.variants = variants;
    }


    public Mock(MockState state, ArrayList<Variant> variants) {
        this.state = state;
        this.variants = variants;
    }
}
