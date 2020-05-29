/**
  This is a test file for checstyle, checkstyle module configured with
  maxLoc = 20, maxCcompl = 5, maxVar = 3, nesting = 4, should fail only for first method,
  which is a brain method, all of the other methods do not satisfy the requirements for brain method, so they shouldn't be marked as such
*/
public class DisharmoniesTest {


    private void loc20_ccompl5_var3_nesting4() {

        // nesting
        for (int i = 0; i < 400; i++) {
            if (i == 40) {
                if (i != 40) {

                }
            }
        }

        int a = 0;
        int b = 1;
        while (b != 0) {
        }
        while (a != 1) {
        }
        System.out.println("dummy");
        System.out.println("dummy");
        System.out.println("dummy");
        System.out.println("dummy");
        System.out.println("dummy");
    }

    private void loc19_ccompl5_var3_nesting4() {

        // nesting
        for (int i = 0; i < 400; i++) {
            if (i == 40) {
                if (i != 40) {

                }
            }
        }

        int a = 0;
        int b = 1;
        while (b != 0) {
        }
        while (a != 1) {
        }
        System.out.println("dummy");
        System.out.println("dummy");
        System.out.println("dummy");
        System.out.println("dummy");
    }

    private void loc20_ccompl5_var3_nesting3() {

        // nesting
        for (int i = 0; i < 400; i++) {
            if (i == 40) {

            }
        }

        int a = 0;
        int b = 1;
        while (b != 0) {
        }
        while (a != 1) {
        }
        while (a != 2) {
        }
        System.out.println("dummy");
        System.out.println("dummy");
        System.out.println("dummy");
        System.out.println("dummy");
        System.out.println("dummy");
    }

    private void loc20_ccompl5_var2_nesting4() {

        // nesting
        for (int i = 0; i < 400; i++) {
            if (i == 40) {
                if (i != 40) {
                }
            }
        }

        int a = 0;
        while (a != -1) {
        }
        while (a != 1) {
        }
        while (a != 2) {
        }
        System.out.println("dummy");
        System.out.println("dummy");
        System.out.println("dummy");
        System.out.println("dummy");
    }

}
