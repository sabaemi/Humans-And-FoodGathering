package behaviors;
import java.util.*;
import java.util.function.Supplier;
import agents.AreaAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import agents.PlanterAgent;
import jade.core.behaviours.*;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;

import jade.lang.acl.*;

public class CollectorBehavior extends TickerBehaviour {
    private long period;
    private Agent myAgent;
    private int x;
    private int y;
    private int aa, bb;
    int lx = 0, lx2 = 0;
    int ly = 19;
    int collected = 0;
    ArrayList<List<Integer>> address = new ArrayList<List<Integer>>();
    int it = 0, bestlx = 0, bestly = 0, best = 0;
    int number;

    public CollectorBehavior(Agent a, long period, int x, int y) {
        super(a, period);
        this.period = period;
        this.myAgent = a;
        this.x = x;
        this.y = y;
        aa = 0;
        bb = 0;


        String str = myAgent.getLocalName();
        String[] arrOfStr = str.split("collectorAgent", 2);
        number = Integer.parseInt(arrOfStr[1]);
        switch (number) {
            case 1:
                lx = 19;
                lx2 = 14;
                break;
            case 2:
                lx = 13;
                lx2 = 7;
                break;
            case 3:
                lx = 6;
                lx2 = 0;
                break;
        }
    }

    @Override
    protected void onTick() {
//        System.out.println("This is agent " + myAgent.getLocalName() + " on ticker after " + period / 1000 + " s.");

        //move
        int ox = x;
        int oy = y;
        switch (collected) {
            case 0:
                if (best == 0) {
                    if (bb == 0) {
                        if (x == lx2 && y == 0 && aa == 1) {
                            x++;
                            aa = 0;
                            bb = 1;
                        } else if (x == lx && y == 0 && aa == 0) {
                            x--;
                            aa = 1;
                            bb = 1;
                        } else if (x > lx2 && aa == 1) {
                            x--;
                        } else if (x == lx && aa == 1) {
                            x--;
                        } else if (x == lx2 && aa == 1) {
                            y--;
                            aa--;
                        } else if (x == lx && aa == 0) {
                            y--;
                            aa++;
                        } else if (x < lx && aa == 0) {
                            x++;
                        }
                    } else {
                        if (x == lx2 && y == 19 && aa == 1) {
                            x++;
                            aa = 0;
                            bb = 0;
                        } else if (x == lx && y == 19 && aa == 0) {
                            x--;
                            aa = 1;
                            bb = 0;
                        } else if (x > lx2 && aa == 1) {
                            x--;
                        } else if (x == lx && aa == 1) {
                            x--;
                        } else if (x == lx2 && aa == 1) {
                            y++;
                            aa--;
                        } else if (x == lx && aa == 0) {
                            y++;
                            aa++;
                        } else if (x < lx && aa == 0) {
                            x++;
                        }
                    }
                } else {
                    if (x < bestlx) x++;
                    else if (x > bestlx) x--;
                    else if (x == bestlx) {
                        if (y < bestly) y++;
                        else if (y > bestly) y--;
                    }
                }
                break;
            case 1:
                if (x < 10) x++;//./9
                else if (x > 10) x--;//./9
                else if (x == 10) {//./9
                    if (y < 9) y++;
                    else if (y > 9) y--;
                }
                break;
        }

        //check different happenings
        if (AreaAgent.array[x][y] == 0 || AreaAgent.array[x][y] == 4) {
            if (AreaAgent.array[ox][oy] == 30 + number) AreaAgent.array[ox][oy] = 0;
            else if (AreaAgent.array[ox][oy] == 430 + number) AreaAgent.array[ox][oy] = 4;
            else if (AreaAgent.array[ox][oy] == 1) AreaAgent.array[ox][oy] = 1;
            //.................Line 140.................
            if (AreaAgent.array[x][y] == 4) {
                //collect food
                if (collected == 0) {
                    AreaAgent.array[x][y] = 30 + number;
                    collected = 1;
                    if (x == bestlx && y == bestly) {
                        List<Integer> bb = new ArrayList<Integer>();
                        bb.add(bestlx);
                        bb.add(bestly);
                        address.remove(bb);
                        best = 0;
                    }
                } else {
                    List<Integer> check = new ArrayList<Integer>();
                    check.add(x);
                    check.add(y);
                    int pos = address.indexOf(check);
                    if (pos == -1) {
                        address.add(check);
                    }
                    AreaAgent.array[x][y] = 430 + number;
                }
            } else if (AreaAgent.array[x][y] == 0) {
                if (x == bestlx && y == bestly) {
                    List<Integer> bb = new ArrayList<Integer>();
                    bb.add(bestlx);
                    bb.add(bestly);
                    address.remove(bb);
                    best = 0;
                    if (collected == 0 && (address != null) && !address.isEmpty()) {
                        //find the best location
                        List<Integer> b = new ArrayList<Integer>();
                        for (List<Integer> list : address) {
                            for (int i = 0; i < list.size(); i = i + 2) {
                                int k = Math.abs(list.get(i) - 10) + Math.abs(list.get(i + 1) - 9);//./9
                                b.add(k);
                            }
                        }
                        int min = b.get(0);
                        int ii = 0;
                        for (int i = 0; i < b.size(); i++) {
                            if (b.get(i) < min) {
                                min = b.get(i);
                                ii = i;
                            }
                        }
                        bestlx = address.get(ii).get(0);
                        bestly = address.get(ii).get(1);
                        best = 1;
                    }
                }
                AreaAgent.array[x][y] = 30 + number;
            }
        } else if (AreaAgent.array[x][y] == 1) {
            if (AreaAgent.array[ox][oy] == 30 + number) AreaAgent.array[ox][oy] = 0;
            else if (AreaAgent.array[ox][oy] == 430 + number) AreaAgent.array[ox][oy] = 4;
            //.................Line 162.................
            if (collected == 1) {
                //store food
                collected = 0;
                AreaAgent.StoredFood++;
            }
            if ((address != null) && !address.isEmpty()) {
                //find the best location
                List<Integer> b = new ArrayList<Integer>();
                for (List<Integer> list : address) {
                    for (int i = 0; i < list.size(); i = i + 2) {
                        int k = Math.abs(list.get(i) - 10) + Math.abs(list.get(i + 1) - 9);//./9
                        b.add(k);
                    }
                }
                int min = b.get(0);
                int ii = 0;
                for (int i = 0; i < b.size(); i++) {
                    if (b.get(i) < min) {
                        min = b.get(i);
                        ii = i;
                    }
                }
                bestlx = address.get(ii).get(0);
                bestly = address.get(ii).get(1);
                if (bestlx > 10) {//./9
                    if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) x++;
                } else if (bestlx < 10) {//./9
                    if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) x--;
                }
                if (bestlx == 10) {//./9
                    if (bestly > 9) {
                        if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) y++;
                    } else {
                        if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) y--;
                    }
                }
                best = 1;
            } else {
                //now Back to their own Territory
                switch (number) {
                    case 1:
                        if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                            y++;
                        } else if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                            y--;
                        } else if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                            x++;
                            aa = 0;
                        } else if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                            x--;
                            aa = 1;
                        }
                        //................Line 208.................
                        break;
                    case 2:
                        if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                            x++;
                            aa = 0;
                        } else if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                            x--;
                            aa = 1;
                        } else if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                            y++;
                        } else if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                            y--;
                        }
                        //.................Line 180.................
                        break;
                    case 3:
                        if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                            x--;
                            aa = 1;
                        } else if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                            y--;
                        } else if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                            y++;
                        }
                        else if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                            x++;
                            aa = 1;
                        }
                        //.................Line 193.................
                        break;
                }
            }
            if (AreaAgent.array[x][y] == 4) {
                if (collected == 0) {
                    AreaAgent.array[x][y] = 30 + number;
                    collected = 1;
                    if (x == bestlx && y == bestly) {
                        List<Integer> bb = new ArrayList<Integer>();
                        bb.add(bestlx);
                        bb.add(bestly);
                        address.remove(bb);
                        best = 0;
                    }
                } else {
                    List<Integer> check = new ArrayList<Integer>();
                    check.add(x);
                    check.add(y);
                    int pos = address.indexOf(check);
                    if (pos == -1) {
                        address.add(check);
                    }
                    AreaAgent.array[x][y] = 430 + number;
                }
            } else if (AreaAgent.array[x][y] == 0) {
                if (x == bestlx && y == bestly) {
                    List<Integer> bb = new ArrayList<Integer>();
                    bb.add(bestlx);
                    bb.add(bestly);
                    address.remove(bb);
                    best = 0;
                    if (collected == 0 && (address != null) && !address.isEmpty()) {
                        //find the best location
                        List<Integer> b = new ArrayList<Integer>();
                        for (List<Integer> list : address) {
                            for (int i = 0; i < list.size(); i = i + 2) {
                                int k = Math.abs(list.get(i) - 10) + Math.abs(list.get(i + 1) - 9);//./9
                                b.add(k);
                            }
                        }
                        int min = b.get(0);
                        int ii = 0;
                        for (int i = 0; i < b.size(); i++) {
                            if (b.get(i) < min) {
                                min = b.get(i);
                                ii = i;
                            }
                        }
                        bestlx = address.get(ii).get(0);
                        bestly = address.get(ii).get(1);
                        best = 1;
                    }
                }
                AreaAgent.array[x][y] = 30 + number;
            }

        } else {
            if (AreaAgent.array[ox][oy] == 1) AreaAgent.array[ox][oy] = 1;
            else if (AreaAgent.array[ox][oy] == 30 + number) AreaAgent.array[ox][oy] = 0;
            else if (AreaAgent.array[ox][oy] == 430 + number) AreaAgent.array[ox][oy] = 4;
            else {
                //.................Line 298.................
                System.out.println(ox);
                System.out.println(oy);
                System.out.println(AreaAgent.array[ox][oy]);
                System.out.println(x);
                System.out.println(y);
                System.out.println(AreaAgent.array[x][y]);
            }
            x = ox;
            y = oy;
            if (x < 19 && x > 0) {
                if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                    x++;
//                    aa = 0;
                } else if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                    x--;
//                    aa = 1;
                } else {
                    if (y < 19 && y > 0) {
                        if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                            y++;
                        } else if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                            y--;
                        }
                    } else if (y == 19) {
                        if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                            y--;
                        }
                    } else if (y == 0) {
                        if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                            y++;
                        }
                    }
                    //.................Line 326.................
                }
            } else if (x == 19) {
                if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                    x--;
//                    aa = 1;
                } else {
                    if (y < 19 && y > 0) {
                        if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                            y++;
                        } else if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                            y--;
                        }
                    } else if (y == 19) {
                        if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                            y--;
                        }
                    } else if (y == 0) {
                        if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                            y++;
                        }
                    }
                    //................Line 353.................
                }
            } else if (x == 0) {
                if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                    x++;
//                    aa = 0;
                } else {
                    if (y < 19 && y > 0) {
                        if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                            y++;
                        } else if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                            y--;
                        }
                    } else if (y == 19) {
                        if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                            y--;
                        }
                    } else if (y == 0) {
                        if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                            y++;
                        }
                    }
                    //................Line 380.................
                }
            }
            //.................Line 383.................

            if (AreaAgent.array[x][y] == 4) {
                if (collected == 0) {
                    AreaAgent.array[x][y] = 30 + number;
                    collected = 1;
                    if (x == bestlx && y == bestly) {
                        List<Integer> bb = new ArrayList<Integer>();
                        bb.add(bestlx);
                        bb.add(bestly);
                        address.remove(bb);
                        best = 0;
                    }
                } else {
                    List<Integer> check = new ArrayList<Integer>();
                    check.add(x);
                    check.add(y);
                    int pos = address.indexOf(check);
                    if (pos == -1) {
                        address.add(check);
                    }
                    AreaAgent.array[x][y] = 430 + number;
                }
            } else if (AreaAgent.array[x][y] == 0) {
                if (x == bestlx && y == bestly) {
                    List<Integer> bb = new ArrayList<Integer>();
                    bb.add(bestlx);
                    bb.add(bestly);
                    address.remove(bb);
                    best = 0;
                    if (collected == 0 && (address != null) && !address.isEmpty()) {
                        //find the best location
                        List<Integer> b = new ArrayList<Integer>();
                        for (List<Integer> list : address) {
                            for (int i = 0; i < list.size(); i = i + 2) {
                                int k = Math.abs(list.get(i) - 10) + Math.abs(list.get(i + 1) - 9);//./9
                                b.add(k);
                            }
                        }
                        int min = b.get(0);
                        int ii = 0;
                        for (int i = 0; i < b.size(); i++) {
                            if (b.get(i) < min) {
                                min = b.get(i);
                                ii = i;
                            }
                        }
                        bestlx = address.get(ii).get(0);
                        bestly = address.get(ii).get(1);
                        best = 1;
                    }
                }
                AreaAgent.array[x][y] = 30 + number;
            }
        }
//        receive location from seeker
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            String str = msg.getContent();
            String[] arrOfStr = str.split(",", 2);
            int messagex = Integer.parseInt(arrOfStr[0]);
            int messagey = Integer.parseInt(arrOfStr[1]);
            List<Integer> check = new ArrayList<Integer>();
            check.add(messagex);
            check.add(messagey);
            int pos = address.indexOf(check);
            if (pos == -1) {
                address.add(check);
            }

            if (collected == 0 && best == 0) {
                //find the best location
                List<Integer> b = new ArrayList<Integer>();
                for (List<Integer> list : address) {
                    for (int i = 0; i < list.size(); i = i + 2) {
                        int k = Math.abs(list.get(i) - 10) + Math.abs(list.get(i + 1) - 9);//./9
                        b.add(k);
                    }
                }
                int min = b.get(0);
                int ii = 0;
                for (int i = 0; i < b.size(); i++) {
                    if (b.get(i) < min) {
                        min = b.get(i);
                        ii = i;
                    }
                }
                bestlx = address.get(ii).get(0);
                bestly = address.get(ii).get(1);
                best = 1;
            }
        }
    }

}