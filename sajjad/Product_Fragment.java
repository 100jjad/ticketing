package com.example.ssoheyli.ticketing_newdesign.Products;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_GetProductList;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Category_Level1;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Category_Level1;
import com.example.ssoheyli.ticketing_newdesign.Model.ProductListModel;
import com.example.ssoheyli.ticketing_newdesign.Model.Product_Scroll_Model;
import com.example.ssoheyli.ticketing_newdesign.ProductList;
import com.example.ssoheyli.ticketing_newdesign.Products.Models.Product_List_Post_Model;
import com.example.ssoheyli.ticketing_newdesign.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ssoheyli.ticketing_newdesign.Products.New_Product_List.category_list_in_viewpager;
import static com.example.ssoheyli.ticketing_newdesign.Products.New_Product_List.flag_category;
import static com.example.ssoheyli.ticketing_newdesign.Products.New_Product_List.refresh_flag;
import static com.example.ssoheyli.ticketing_newdesign.Products.New_Product_List.viewPager;
import static com.example.ssoheyli.ticketing_newdesign.Products.Product_list_ViewPager_Adapter.product_position;

public class Product_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    String captcha = "iVBORw0KGgoAAAANSUhEUgAAAIIAAAB5CAYAAAD1VvknAAAAAXNSR0IArs4c6QAAAARnQU1BAACx\n" +
            "jwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAABdISURBVHhe7Z15fBVVlsfPy54QCPsiOwooCoqi\n" +
            "gLg1CuqgLTZif7Bl2ETUmT/amY+DTk9/UKdtaG1EZwSkxWlEwAYBWZRuERERUUFlUQQERRDZdxIS\n" +
            "SPJqzu+8Oi83lXoLIeaFevWVSt2t7r1177nnrvUMWAz5JD0p9t0nyfEFwUdIsCBY/F+QrGDQticf\n" +
            "VrCU/+L9+UpgJ53AMUJICAIiiwHavHkHrVm/nbIy0sXudYpLS6hd66Z0bfdLWQa4CsJNMjHvntjB\n" +
            "IpIOIPkUGvPUTHr6yQVsriVe3qeIfvmr62jhvH9lcwlfaeKaKGqAIMAQoLF/nkv/+dgSotyckBNy\n" +
            "BYNH71bBafr1kG70t7+OYgd0DfBEN5EYgUjsGEG0AQqAsZAVvgIpZOFK8fYdBCytdLsMgonTCokV\n" +
            "BCN5S4TCBkavX1z5AdEEIbOQwNpIsCA4kRIKlYvXL8F+X8E0Vz81TBB8EoUvCD6CLwg+gi8IPoIv\n" +
            "CD6CLwg+gi8IPoJHBcGek4en5qad/+CO5W3buSLqEenORHz2/MRjgsC1Y9nr9qgoLNxIhcPOd6xe\n" +
            "wox7AHf429u/4Qt/EB5m+7lQQP6H4hKPsJNX8JYgoOIKiohOFhDl5/Odr/yTfMF+wnYvDN1xnS4J\n" +
            "VWhpKdGpU3Y4+HHYQrbLCnCAUoJ2resyuIcEQKkxZxb/+Od59DvsPtY+h23ok6fo0cf70EUX1qGS\n" +
            "YouCXHEp8nYWy0iAG3eAUlG77J5dK4MmTVlL6z7eQbXyAjR4SG9q3zGP0lMDVFQYpE/X7KH5s1ex\n" +
            "MHBbyc0NCYwIBe5wRsTn0I7yi2jQP3ejWdMetB0QceLapccE4Qht3PAMde7S2naIzj2DJ9K2HUdo\n" +
            "w6rf2y4V+bfHZ9GEPy0gymlGlBoUpWCJVPAVQOWJhJw9NUwQvNU1MOm1cMIpOpaMI4jOcNfQoHa0\n" +
            "8EF6ftx9tHzVf3PXsY+7ENYqrFMoBV2KSIRn8JYgZOdR9+ueoobNH6QGzUdQ/RYjQvfmw6keXxm5\n" +
            "g2jK1KUUsM8DlHKlpmBcwTz1p4V0RY/R1LTtSOrT7zn6+7KN7IpwpfSLXh1p9dpxLAx7xS7dBYQA\n" +
            "A06P4C1BSE2lEycy6PCRTDpyJJuOHs4O3Y/k0LGjOVRckEWnTpcd/kjjVp3XqD71ufMZevLxv9GG\n" +
            "L/Jp/94sWvaP7+if+vyR7hkygUOxBmAF0rNbW/rrrP9glc4DULj5GqEGgwaawn+g7dP51ewrgIsH\n" +
            "gSAlpeyVMzJTaf6cdbRsyQ4eEDamQBZXcAYPMLNZWHKb0bzp6+jXQ/9HSmnz9kM0+vFpRFmZnA5r\n" +
            "BdEIoXi8gLcEAUjluNSQi1NqClf8Ga7U7Bz252YvrRzjAA6Myq5dj+a8tpbuGDiROrX/LR3Yy+4Z\n" +
            "fAVZ0uSYmXdUgvcE4SwIWlzZ0uezEKBOpZWzIECrWCUsFzxLqFWP3lnwFWuIeqwN0E1kcKmd5nB8\n" +
            "eUglJLUgnDldTLfefQ1lpnO/X1LCcsDFITMKSARXOAYHmCFkswbAABOCA80RRPfAQuEhkloQSoNY\n" +
            "YDpFW74eT1S0h4UB6wQokpAAWIE0FgmucJlZQECgKfgG4fCQNgBJLQgBFoT8wlPUpmUD2rTlFRaG\n" +
            "A2QVFLN7Gg8RbI0gQiB9Bv+zLw+S1IKA7wtS01DhRJ06NibLmk09ezdlYdjLVV8UWk5GvaPyIQth\n" +
            "vCcMSS0IaTyt3Lhht20Lsfr9MTTl/x4iKjhIwWLMH1gCZOfSDuBRkloQctICdPjHE5RRdwjt3XPM\n" +
            "diV6cFhv2ntwBmXWzifrZAGPG0wpKKcaPENSC0IJxgA8ICwuyqMLmg+lv0xdafuUUtOGmVR0aBpd\n" +
            "fWMr7ipO2vWPMQOKDHfYvUNSC0JolZFbeyZf2S1o1MjJdPOtf2C3sqnhmhVPUr97LiPKP8o2aAZ0\n" +
            "FXz3l5i9BBaUuDaDFqVgCTq3AS1fup3qNecxQphSevvNf6fr+lxMdLKIS4yfYQEKDyQ9QpILAkDr\n" +
            "ths3Zge5eXTsQIB7jHuo5AwcQ9rho6W/I8oqZrmA4ARlxuEllZDkgmBUpgwIWQACpZSKX21JbUTp\n" +
            "9YeE/GxenTqUZxPYfeS5xLkcSqmBJLkglFWkfJYv/9IomFJMgfQMrvR0umvA8xqChv/mJrljGVpm\n" +
            "EjXjcFeVkNSCYMlP1mAnkStUZAL3UkrBzCDAfrk5tGj+CipF47eFpmuPNkTFOAOJfQdfI3gC2Ufg\n" +
            "qSC6A1R0AOq+NIX/4VdNMiUEftNp1+5DfA/RuDE/I2NMfxvaM+BX3WRNQMEWszRyHhTiEvMJatmq\n" +
            "IQzC/n3snuqt8QFIIkFwa71coegN7IMmlpwz4FGC2Pni6eLdA/qU+3mr9Wt2hk4+2VrEKySBIERR\n" +
            "3yXplFY/k4IFu4kKea7I3QKGDSXFXMn5h6hlhyyaP/e3HDAUx5Rpy/lvOle/t7oFkBwaIcjTPQvj\n" +
            "gdAXCUrRmRK69ZYOtHvfNOrctTZLAE8NSw9Ty9bZNPkvw2jX1v+1Q4Z+9uqhYVOJcrJDsYQ/f/MG\n" +
            "3hcEnuZZWAa05/3mBlKQR30Fx49T8ya1aePasWSdnsbXTNr17Xh6aOQvOERZRddvMpIoLY8CKawR\n" +
            "MKOQaPyu4TwClYkWDHMx5dTOgkHIycyktDQeC1RABSBAH678ngIZQ+k4thpyWKvw2EG2pg0h8QLe\n" +
            "+uTNFX491gL4VDFYWkKt29WlVk1Z1aem0aatR+nY/kLq27cdjXjwVmrVpK4cez9VWEKfrd1Gz7/w\n" +
            "Dzq48wBRNs8asBfB3UEK65GgbDRwG4J2qaxS8L99dOdnEwS8Hc8E8EVzEBtGxeyASzaPeLqYxoVf\n" +
            "gBlAIV+yuWDDfhmcl3SuaQ4SwA9n80wh9MOgrBnwK6mIo7KS4H/7mBjwZbRUGrqCLO4esrii01GZ\n" +
            "XAG57JXLlZ5bn6gOX7kNKJCby11CSAhQSfLTuRbWGfgZDDwDWGuorDqoeXhfEFBXosJhkKbNNx7s\n" +
            "yTIyt2i5o2IRhoOgYXIYtPzQ/gMuFBOe4/D4rQTE4R9nPw9BBYeBxa708N1A3fVSAQnb1ewtkkMQ\n" +
            "fGLiC4KP4AuCj+ALgo/gC4KP4AuCj+ALgo9QcwQBC3/JTILfP4GCgDfH/wQ0hCU/YJkk0oDXlH2L\n" +
            "MmsQVYH9jASRQEHAZm5oQ1dsUip68sfjF17VSiU5IscEcBoWy9kJPBWd0K4hdHgUhcPIplApF04g\n" +
            "CS68MLazsXEFI6qBhUDOQSaGxG5Dc9L4Ygg/YPP7Z2bSH/5rETtiKxBZkmbj4XsR/XJgL1o451/Y\n" +
            "ynZoA/x+k/1joNVNAgWBk+XWUZqC3z9NpVWrN9Hf391EmdkZoc1C9vbyveR0CV3ZpQ3d3b+bXRrc\n" +
            "UUoAsVY7iRUE/csFkMDuMbHIT/ylchFwt4gDLwmixpxQ8kksCR0s+tQcfEHwEXxB8BF8QfARfEHw\n" +
            "EXxB8BF8QfARfEHwEXxB8BHiXllEsIBjHRhuuMz/T5KJ+uM557MAfko0/8o8C6I9D2L5R0Kfwx3P\n" +
            "xno+Vl41HpNIz5juiukfDOI3ocrH5fZ8hTDsUTFmF9wyBjdnhCax/JV4w0XiXJ+vLs42nwgPIj2j\n" +
            "lW76w81smJHSdLpXEIRly5bR7t27KTs7W+ynTp2i5s2bU9++fcXu5KuvvqKOHTtSRkaGa6LvvPMO\n" +
            "devWjZo0aRL21/v+/ftp3bp1dNttt9mhQ6h/fn4+/fjjj3TJJZeIGzDjx0sj/csvv1zsZvpFRUX0\n" +
            "3nvv0Z133il2Ew2H90xPTy+Xt3goLCykuXPnihnP9e/fn+rUqVMhDrUfO3aMjh49Sm3btrV9yrN+\n" +
            "/Xr68ssvKS0tTSoR9/bt29NVV13lmi+4IXwkf/Dpp59Sjx49xOwM89prr9GQIeV/TBSBXPn8888t\n" +
            "zqBtsywudLmcTJo0yfrkk0/E7Ob/ww8/WHPmzBGzM44333zT+umnn2xbCDPMhx9+aL366qtijsRL\n" +
            "L71kbdu2Tcxm3CzA1syZM21bGWb88F+4cKGYTfdYzJgxw9q7d6+YuYLDeXQ+r/YlS5ZY06dPF3M8\n" +
            "ICw3AttWERZ+64UXXoia36lTp1qfffaZbSsPnnUScbDIiVAp/i/qDIeTuxO05osuuoi2bNniKplw\n" +
            "a926tbTs48ePi7+GgR2t9oILLhC7CcKcOXOGduzYQY0aNaJvv/1W3N3yUbt2bfroo4+kdTvTjzR2\n" +
            "QTiuSNEGJ06coJMnT1Z4NhqnT58WDQDq1q1Lw4cPd80b4kT8hw4dory8PNFukUB5g+3bt1Pjxo2p\n" +
            "Vq3IvxPBFUxXXHGFtPpIZGVl0aZNm+jrr7+2XcpwK5eIgmCCF9LLfGFkqE+fPqK63TKlhQsVxVpD\n" +
            "zPr8qlWrwqrLjZUrV4rKv/3228UcieLiYho2bBi9/fbbUuCx0Dy9++67kvcbbriB3n//fXGLl+uv\n" +
            "v55Ym0jawE0I1G3FihXUq1cvuvHGG4k1nLg5w8Ou+YJQX3vttWJW4K/PoEto166dxIduUd2dcUJY\n" +
            "77//flq7di199913tmtkIgqCZiwSyFCnTp3E3L17d/rmm2/E7AYEBS0WBYd40ceiRUKbOIE/tAF3\n" +
            "KSL16C9btGhBrP5d86QFMGLECOIuiAoKCsTuLBgTHQOhNbdq1UoECHmK9c4A8aL/hhBNnjxZChnP\n" +
            "OZ+FHdoAYwNUHDQCWjneG37O/MFNtQG0nBONH+V+0034TWiirl27SkUrZpwwayPBuG/fvn22jztx\n" +
            "aQQTzRAyUL9+fSmInTt3SqGiq4jEpZdeGs40NMk111wjZlWJJtAWGKBCGFA4TZs2pdWrV9u+7kDN\n" +
            "owVMmzZNurTU1PKnfcxCQsts06YNff/993JdeOGFUljxgrjw/MMPPyzaJNJ7o3XreyCdZs2ahbWb\n" +
            "liNQ88cffywaKhIbNmwQIdm1a5eUS25urggGMONTtGsfOXIkzZs3T7rjSJy1IAC8OF4KLffw4cN0\n" +
            "4MABKcwvvvjCDlG+4AG0BvosgELp0qWLmLW/0vBQaVpoiPfIkSPih5eGO3ATHoB+e+DAgcSDN7Fr\n" +
            "4Zh54cGppIk+FJoAFwQaMxhoBcWZfwVxarwQPhQyBBuzK7hr3qANkH+0cMSNdJBmSUmJaAUnGA8h\n" +
            "H9G0Acq3Q4cO4XJBflu2bEk8sBd/DQfMfOJ9MY5Bd4b3cjYSgT1c4dZrccK2rTwYqbMQ2LYyMMrH\n" +
            "bCMS3F9ar7/+eoXRLBdeeATMUz7XdLlgLZ722LYyXnnlFdtUBmYqcJ8/f77tUjaCZ40RHvGbcAuz\n" +
            "Fi9eLGYN6wZXqMQPNNwbb7xh8UBQzApmI5s3b7ZtZbBQWLNmzbJtZcSaKaBMeFxj28rz8ssv26Yy\n" +
            "MJPhQbCYNZ+Y4aBcpkyZInaTChqB3eQOtcIRiNkE0o8+Eq3BCQZFkE6Nw8nVV18tc2rMf01UetGq\n" +
            "0NVceeWVtk8ZaClo8RgrmKCFmSBtzFR69uwp8akb4sfYAK0BXY0zj9Bo0BaxZhBo1RiYolUiHFQ0\n" +
            "0sE4RuPEuAB98sUXXyx2E2gIPIe0FGg6rMOg5eqag6mdAAbj2m0gHb0AxmrOwboOZBWERfeNdRVo\n" +
            "KyepTzK2OQwyiogwuMHDmijckWlUOAZxTvAieCEIkU6vFDwPv4YNG1KDBg3C8akfzOj7MHhDGAih\n" +
            "0x/dBcKg0BUMDtFfK/qMDrrq1asndrhv3bpVFrd0auaMH1NVqFxn+iZ4h86dO0v/j/EBnsWCkqpb\n" +
            "PIMywpgIA0TzPRXkDYKC9wHoFjIzM2WstWfPnnD3hXIC6ErQbeh7Iz69gJYLGoCiwokGa4ZFF4v3\n" +
            "1LgV1yVmM/PqDbvTXc3ADAei+TvDAjO8W1xA7WYlmXGYaHz6LO4oXKDP67Ox0jPReJ1onGY6bvGY\n" +
            "z0dLB8Tyd4LwGr+ZRjzpnfVxdjNiN8zonOH0WQ1j+ptusdJQoqUFnOm4pQs0PefdDY1D0fBqBmY8\n" +
            "wIzL+byi4c2wwAzv9DNxPuuWdjTOWhB8vEmlpo8+3sMXBB/BFwQfocrGCG7RRBq8wKx3xXQ/H3Dm\n" +
            "vSow4wRmvOpXmbTiKecqEQS3TDoTc07Z3KZwuHTqVdNxe+efi+pIq0pK3ZlB506eCgUubMAAVLiG\n" +
            "wSKO+p8vaH6x34KrKsECk4mmhXKqDFgt1RVYbDyhETqpsuaHjI4aNUrubucH4L548WI5roWNE4Dl\n" +
            "aizZui3Fng9glRIrgm6bSJUFW8vYtsaWurJkyRJpOJGOC0YDh4KwyosVRqwWY6XYbdOpSvWw7oJF\n" +
            "Ol2D9XygB1Kw9o/dRnMZ+HxCd1shDFWFlo02FoATWtCqWk5nA5aUsSyOxqbLzW4Nr0oFAQm6gcTx\n" +
            "ItgcwX369Onibq7PJwLkBZeJm1t1gsMuSB+HchTd4HMrJzOvmnd10zsa2+bNm8WMbkHNJtUyMjMz\n" +
            "C9z6qOpG8+QsXLirm+7SOfPvJJb/2WDG5dxBNHHLP8xOuxtu8VaLIGgGMajCTmFOTk7YPZEgfQxs\n" +
            "MZhS0BejxZi7fbGoyvdAXNiFxO6gnuJyA+HQZfTu3VuO4+OCGdvi8HMTTrwnyt+ti4lbELBPrieE\n" +
            "nMQ7asbWKF6yKgsuGmZhOE8QIw/33nuvCCWOiJlgSxdnAtxOC1UHONGEGUKkrhbguB2+J/nggw9k\n" +
            "JgDtBTPOimCs5lbGeCeUv+sRAvseE0gRBnsoJGXs2LGS4FtvvWW7REfXCFyPSlUxEALkTQUPB2id\n" +
            "6OAWMxcTZ0G5FerPiZZPtDUVPcCKM5r4LACa7dlnnxW3++67T+5OND6394lbEAYMGCCFiYMjytKl\n" +
            "S+XuLMiagL6szp/d1GG0FleT0cOyOK9ofrH02GOPyd15iiseKgiCs29ROz7xch5Zr4kC4EQFojKt\n" +
            "uro1QbyoJtPpuBs4LKu4jRecVBAEvPz8+fPlfBy+koE9UkTxJHA+oiq0umc3Wp4qgJHSj6bi3Z7V\n" +
            "eLXLc6s3164B3QDO5MVbEG4Zigc9d+82eKkqdHHLrb/VvlhnMQo+SgV6pjAWzvOZlUXLUcdckRaQ\n" +
            "8JUTwFdUTrSSzYGuvrsOis0znoprDeCULw5Xxqpg9Y9XMzjDXXbZZXLH9w7PPfecTNlUOKoCCBhG\n" +
            "4ACHXBctWhReG8DBUsyEwOzZs6XbQ9r4YGTSpEni/vTTT8s9EuijMRsZNGiQNB4s3MRbFk4wXkFX\n" +
            "i9PIL774orhhMG6iceMLMSxvo4vAQVuEg8DjnZQZM2bIqiLeCQKPccOYMWPET+/l4Mgr0L17d6Ro\n" +
            "sQSJnTWD3J3069dPwrEEi71Tp05iX7Zsmdid4Jw9/LkF2i6WNWzYMHGraZf5xXCk91+zZo3rs1Vx\n" +
            "6VfagBuJuKFeTPr371/huVgXC5n9dHlct6GfeOIJGjduHD3yyCM0ceLEClKumgAtGq0Zv0Nwyy23\n" +
            "iKrC/gE+XDWPS+N5PIPFDsxz9esfBQMbbFTF0kDnAuJ2edVyoPVgAIaNH6DhY+Vr+fLlol3ONf/o\n" +
            "irE2oJpSwbce2KDDcXV8SqDlqeAD44MHD8r1wAMPyHedCxYskHUbhMV7oTuAFgHO5wV2rMCuXbtE\n" +
            "enBxRduu5cFXRBoGX9CYFBcX26byqAZhVWq7+Jwt0E6Rynfo0KFSvoMHD7Zd4qeCRoAV0vLoo48S\n" +
            "q0dxgyTddddd8mEEtlwxmNm4caP44Zs6/dZQwUoXpBfb0mhd6DvHjx8vn3EDLICYO3aappoTybm2\n" +
            "6nPNf7T0zXLCQPaOO+6QZWWMASZMmBD+2gmaBeHMvMR8Lw4cEXxrmJ6eLlLmdqHvcqL96ejRoyuE\n" +
            "b9WqlXw7CMx+1zT7xAfqxlm+3OVW0M7xEtdRNfQ1GAegJWNke/PNN8uI1A2NDhKI8BipY/qCcYNO\n" +
            "5cwwPucGyheHTzClxCf4oDLlG1UQYkVoPqphoj3jTMoXhKonVp1FIi6N4ON9XFcWfZIPXxB8BF8Q\n" +
            "fBii/weJi1IwJ+2hQQAAAABJRU5ErkJggg==";

    Category_Level_2_List_Adapter update;
    ArrayList<Product_Scroll_Model> temp = new ArrayList<>();
    ImageView imv1;
    RecyclerView rcv;
    TextView txv1;
    String TAG;
    static int pos;
    List<Model_GetProductList> list;

    private ProgressBar loader;
    //    FloatingActionButton fbn_scroll_to_top;
    List<Model_Get_Category_Level1> category_list;
    private SwipeRefreshLayout swipeRefreshLayout;
    Product_List_Adapter adapter = new Product_List_Adapter(getContext());

    public static Product_Fragment newInstance(int page_position) {
        pos = page_position;
        Product_Fragment fragment = new Product_Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_fragment, container, false);
        txv1 = v.findViewById(R.id.tv1);
        rcv = v.findViewById(R.id.rv1);
        imv1 = v.findViewById(R.id.im1);

        loader = v.findViewById(R.id.loader);
        Sprite circle = new Circle();
        circle.setColor(ContextCompat.getColor(getActivity().getApplicationContext(), Constans.colorprimary));
        loader.setIndeterminateDrawable(circle);

        update = new Category_Level_2_List_Adapter(getActivity().getApplicationContext());
//        fbn_scroll_to_top = v.findViewById(R.id.floatactionbutton);


        if (flag_category) {
            if (category_list_in_viewpager.get(viewPager.getCurrentItem()).getChild_count() != 0) {
                loader.setVisibility(View.VISIBLE);
                get_category_list(category_list_in_viewpager.get(viewPager.getCurrentItem()).getCategory_id());
            } else {
                update.entries = new ArrayList<>();
                update.notifyDataSetChanged();
                rcv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                rcv.setAdapter(update);
            }
//            fbn_scroll_to_top.hide();
        } else {
            loader.setVisibility(View.VISIBLE);
            get_Product_list(category_list_in_viewpager.get(viewPager.getCurrentItem()).getCategory_id());
//            fbn_scroll_to_top.show();
        }


        return v;
    }

    private boolean isViewShown = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            isViewShown = true;
            if (flag_category) {
                if (category_list_in_viewpager.get(viewPager.getCurrentItem()).getChild_count() != 0) {
                    loader.setVisibility(View.VISIBLE);
                    get_category_list(category_list_in_viewpager.get(viewPager.getCurrentItem()).getCategory_id());
                } else {
                    update.entries = new ArrayList<>();
                    update.notifyDataSetChanged();
                    rcv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    rcv.setAdapter(update);
                }
//            fbn_scroll_to_top.hide();
            } else {
                loader.setVisibility(View.VISIBLE);
                get_Product_list(product_position);
            }

            if (refresh_flag) {
                if (flag_category) {
                    if (category_list_in_viewpager.get(viewPager.getCurrentItem()).getChild_count() != 0) {
                        loader.setVisibility(View.VISIBLE);
                        get_category_list(category_list_in_viewpager.get(viewPager.getCurrentItem()).getCategory_id());
                    } else {
                        update.entries = new ArrayList<>();
                        update.notifyDataSetChanged();
                        rcv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                        rcv.setAdapter(update);
                    }
//            fbn_scroll_to_top.hide();
                } else {
                    loader.setVisibility(View.VISIBLE);
                    get_Product_list(product_position);
                }

                refresh_flag = false;
            }
        } else {
            isViewShown = false;
        }
    }

    public void get_category_list(int position) {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Model_Post_Category_Level1 model_post = new Model_Post_Category_Level1();
        model_post.setCategory_id(position);
        model_post.setLanguage_id(1);
        Call<List<Model_Get_Category_Level1>> model = api.get_category_level1(model_post);

        model.enqueue(new Callback<List<Model_Get_Category_Level1>>() {
            @Override
            public void onResponse(Call<List<Model_Get_Category_Level1>> call, Response<List<Model_Get_Category_Level1>> response) {
                if (response.isSuccessful()) {
                    category_list = response.body();
                    ArrayList<Product_Scroll_Model> temp;
                    temp = new ArrayList<>();
                    for (int i = 0; i < category_list.size(); i++) {
                        Product_Scroll_Model entry = new Product_Scroll_Model(category_list.get(i).getCategory_title(), category_list.get(i).getCategory_id(), category_list.get(i).getChild_count(), i);
                        temp.add(entry);
                    }
                    update.entries = new ArrayList<>(temp);
                    rcv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    update.notifyDataSetChanged();
                    rcv.setAdapter(update);
                } else {
                    Log.d(TAG, "Response" + response.toString());
                    try {
                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.server_1061), Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException exeption) {
                        exeption.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Model_Get_Category_Level1>> call, Throwable t) {
                String te = t.toString();
                try {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.server_1062), Toast.LENGTH_SHORT).show();
                } catch (NullPointerException exeption) {
                    exeption.printStackTrace();
                }
            }
        });

        loader.setVisibility(View.INVISIBLE);
        update.entries = new ArrayList<>(temp);
        rcv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        rcv.setAdapter(update);
//        if (update.entries.size() == 0) {
//            imv1.setVisibility(View.VISIBLE);
//            imv1.setImageResource(R.drawable.ti1);
//            txv1.setText(getString(R.string.nothing_exist));
//        } else {
//            imv1.setVisibility(View.GONE);
//            txv1.setText("");
//        }
    }

    public void get_Product_list(int position) {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit2.create(API.class);
        Product_List_Post_Model post_model = new Product_List_Post_Model(
                position,
                Config.getLCID(getActivity().getApplicationContext()),
                0,
                10
        );
        Call<List<Model_GetProductList>> model = api.getProductList(post_model);
        model.enqueue(new Callback<List<Model_GetProductList>>() {
            @Override
            public void onResponse(Call<List<Model_GetProductList>> call, Response<List<Model_GetProductList>> response) {
                loader.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    list = response.body();

                    ArrayList<ProductListModel> temp;
                    temp = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        String price = edit_price("" + list.get(i).getPrice());
                        try {
                            byte[] bytearray = list.get(i).getAttachfile().getImage();

                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
                            ProductListModel enrty = new ProductListModel("" + list.get(i).getName(), price, "" + list.get(i).getDiscount(), "" + list.get(i).getNumber(), bitmap);
                            temp.add(enrty);
                        } catch (NullPointerException exception) {
                            byte[] bytearray = Base64.decode(captcha, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
                            ProductListModel enrty = new ProductListModel("" + list.get(i).getName(), price, "" + list.get(i).getDiscount(), "" + list.get(i).getNumber(), bitmap);
                            temp.add(enrty);
                        }

                    }
                    adapter.entries = new ArrayList<>(temp);
                    rcv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    update.notifyDataSetChanged();
                    rcv.setAdapter(adapter);

                    if (adapter.entries.size() == 0) {
                        imv1.setVisibility(View.VISIBLE);
                        imv1.setImageResource(R.drawable.ti1);
                        txv1.setText(getString(R.string.product_list_no_item));
                    } else {
                        imv1.setVisibility(View.GONE);
                        txv1.setText("");
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.server_1021), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Model_GetProductList>> call, Throwable t) {
                loader.setVisibility(View.INVISIBLE);
                Log.d(TAG, "Response" + t.getMessage());
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.server_1022), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (flag_category) {
                        get_category_list(product_position);
                    } else {
                        get_Product_list(product_position);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                } catch (NullPointerException exeption) {
                    exeption.printStackTrace();
                }
            }
        }, 2000);
    }

    public String edit_price(String price) {
        int lengh = price.length();
        StringBuilder price2 = new StringBuilder(price);
        while (lengh > 3) {
            lengh = lengh - 3;
            price2.insert(lengh, ",");
        }

        return price2.toString();
    }


    public static void update_fragment(Context context) {
//
//        if(flag_category)
//        {
//            loader.setVisibility(View.VISIBLE);
//            get_category_list(category_list_in_viewpager.get(New_Product_List.viewPager.getCurrentItem()).getCategory_id());
////            fbn_scroll_to_top.hide();
//        }
//        else
//        {
//            loader.setVisibility(View.VISIBLE);
//            get_Product_list(product_position);
////            fbn_scroll_to_top.show();
//        }
    }

}
