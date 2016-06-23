package com.coderskitchen.jgroups;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.View;

public class JGroupsLeaderElectionExample {

  private static final int MAX_ROUNDS = 1_000;
  private static final int SLEEP_TIME_IN_MILLIS = 1000;

  public static void main(String[] args) throws Exception {
    JChannel channel = new JChannel();
    channel.connect("The Test Cluster");
    for (int round = 0; round < MAX_ROUNDS; round++) {
      checkLeaderStatus(channel);
      sleep();
    }

    channel.close();
  }

  private static void sleep() {
    try {
      Thread.sleep(SLEEP_TIME_IN_MILLIS);
    } catch (InterruptedException e) {
      // Ignored
    }
  }

  private static void checkLeaderStatus(JChannel channel) {
    View view = channel.getView();
    Address address = view.getMembers()
                          .get(0);
    if (address.equals(channel.getAddress())) {
      System.out.println("I'm (" + channel.getAddress() + ") the leader");
    } else {
      System.out.println("I'm (" + channel.getAddress() + ") not the leader");
    }
  }
}
