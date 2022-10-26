package behaviors;
import agents.AreaAgent;
import agents.PlanterAgent;
import agents.SeekerAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;

import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.*;

import jade.lang.acl.*;

public class SeekerBehavior extends TickerBehaviour {
    private long period;
    private Agent myAgent;
    private int x;
    private int y, aa, bb;

    public SeekerBehavior(Agent a, long period, int x, int y) {
        super(a, period);
        this.period = period;
        this.myAgent = a;
        this.x = x;
        this.y = y;
        aa = 0;
        bb = 0;
    }

    @Override
    protected void onTick() {
        //move
        int ox = x;
        int oy = y;
        if (bb == 0) {
            if (x == 0 && y == 19 && aa == 1) {
                x++;
                aa = 0;
                bb = 1;
            } else if (x == 19 && y == 19 && aa == 0) {
                x--;
                aa = 1;
                bb = 1;
            } else if (x > 0 && aa == 1) {
                x--;
            } else if (x == 19 && aa == 1) {
                x--;
            } else if (x == 0 && aa == 1) {
                y++;
                aa--;
            } else if (x == 19 && aa == 0) {
                y++;
                aa++;
            } else if (x < 19 && aa == 0) {
                x++;
            }
        } else {
            if (x == 0 && y == 0 && aa == 1) {
                x++;
                aa = 0;
                bb = 0;
            } else if (x == 19 && y == 0 && aa == 0) {
                x--;
                aa = 1;
                bb = 0;
            } else if (x > 0 && aa == 1) {
                x--;
            } else if (x == 19 && aa == 1) {
                x--;
            } else if (x == 0 && aa == 1) {
                y--;
                aa--;
            } else if (x == 19 && aa == 0) {
                y--;
                aa++;
            } else if (x < 19 && aa == 0) {
                x++;
            }
        }

        //check different happenings
        if (AreaAgent.array[x][y] == 0 || AreaAgent.array[x][y] == 4) {
            if (AreaAgent.array[ox][oy] == 2) AreaAgent.array[ox][oy] = 0;
            else if (AreaAgent.array[ox][oy] == 24) AreaAgent.array[ox][oy] = 4;
            else if (AreaAgent.array[ox][oy] == 1) AreaAgent.array[ox][oy] = 1;
//            .................Line s1.................
            if (AreaAgent.array[x][y] == 4) {
                //send message to collector agent
                int collectorname = 2;
                if (x >= 0 && x <= 6) collectorname = 3;
                if (x >= 7 && x <= 13) collectorname = 2;
                if (x >= 14 && x <= 19) collectorname = 1;
                ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                cfp.addReceiver(new AID("collectorAgent" + collectorname, AID.ISLOCALNAME));
                cfp.setContent(x + "," + y);
                myAgent.send(cfp);
                AreaAgent.array[x][y] = 24;
            } else AreaAgent.array[x][y] = 2;
        } else if (AreaAgent.array[x][y] == 1) {
//            AreaAgent.array[ox][oy]=0;
            if (AreaAgent.array[ox][oy] == 2) AreaAgent.array[ox][oy] = 0;
            else if (AreaAgent.array[ox][oy] == 24) AreaAgent.array[ox][oy] = 4;
//            .................Line s2.................
            switch (aa) {
                case 0:
                    if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                        x++;
                    } else if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                        y++;
                    } else if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                        y--;
                    } else if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                        x--;
                    }
//                    .................Line s8.................
                    break;
                case 1:
                    if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                        x--;
                    } else if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                        y++;
                    } else if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                        y--;
                    } else if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                        x++;
                    }
//                    .................Line s9.................
                    break;
            }
//            AreaAgent.array[x][y]=2;
            if (AreaAgent.array[x][y] == 4) {
                //send message to collector agent
                int collectorname = 2;
                if (x >= 0 && x <= 6) collectorname = 3;
                if (x >= 7 && x <= 13) collectorname = 2;
                if (x >= 14 && x <= 19) collectorname = 1;
                ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                cfp.addReceiver(new AID("collectorAgent" + collectorname, AID.ISLOCALNAME));
                cfp.setContent(x + "," + y);
                myAgent.send(cfp);
                AreaAgent.array[x][y] = 24;
            } else if (AreaAgent.array[x][y] == 0) AreaAgent.array[x][y] = 2;
        } else {
//            AreaAgent.array[ox][oy]=0;
            if (AreaAgent.array[ox][oy] == 1) AreaAgent.array[ox][oy] = 1;
            else if (AreaAgent.array[ox][oy] == 2) AreaAgent.array[ox][oy] = 0;
            else if (AreaAgent.array[ox][oy] == 24) AreaAgent.array[ox][oy] = 4;
            else {
//                .................Line s3.................
                System.out.println(ox);
                System.out.println(oy);
                System.out.println(AreaAgent.array[ox][oy]);
                System.out.println(x);
                System.out.println(y);
                System.out.println(AreaAgent.array[x][y]);
            }
            x = ox;
            y = oy;
            if (y < 19 && y > 0) {
                if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                    y++;
                } else if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                    y--;
                } else {
                    if (x < 19 && x > 0) {
                        if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                            x++;
                        } else if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                            x--;
                        }
                    } else if (x == 19) {
                        if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                            x--;
                        }
                    } else if (x == 0) {
                        if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                            x++;
                        }
                    }
//                    .................Line s4.................
                }
            } else if (y == 19) {
                if (AreaAgent.array[x][y - 1] == 0 || AreaAgent.array[x][y - 1] == 4) {
                    y--;
                } else {
                    if (x < 19 && x > 0) {
                        if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                            x++;
                        } else if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                            x--;
                        }
                    } else if (x == 19) {
                        if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                            x--;
                        }
                    } else if (x == 0) {
                        if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                            x++;
                        }
                    }
//                    ................Line s5.................
                }
            } else if (y == 0) {
                if (AreaAgent.array[x][y + 1] == 0 || AreaAgent.array[x][y + 1] == 4) {
                    y++;
                } else {
                    if (x < 19 && x > 0) {
                        if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                            x++;
                        } else if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                            x--;
                        }
                    } else if (x == 19) {
                        if (AreaAgent.array[x - 1][y] == 0 || AreaAgent.array[x - 1][y] == 4) {
                            x--;
                        }
                    } else if (x == 0) {
                        if (AreaAgent.array[x + 1][y] == 0 || AreaAgent.array[x + 1][y] == 4) {
                            x++;
                        }
                    }
//                    .................Line s6.................
                }
            }
//            .................Line s7.................
            if (AreaAgent.array[x][y] == 4) {
                //send message to collector agent
                int collectorname = 2;
                if (x >= 0 && x <= 6) collectorname = 3;
                if (x >= 7 && x <= 13) collectorname = 2;
                if (x >= 14 && x <= 19) collectorname = 1;
                ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                cfp.addReceiver(new AID("collectorAgent" + collectorname, AID.ISLOCALNAME));
                cfp.setContent(x + "," + y);
                myAgent.send(cfp);
                AreaAgent.array[x][y] = 24;
            } else if (AreaAgent.array[x][y] == 0) AreaAgent.array[x][y] = 2;

        }
  }

}