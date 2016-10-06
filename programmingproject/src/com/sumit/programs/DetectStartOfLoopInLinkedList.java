package com.sumit.programs;

/**
 * Definition for singly-linked list.
 * 1. First detect there is loop in linked list
 * 2. Then reset 1 counter to head, and start incrementing both counters by 1. The pt. they meet is starting of loop
 */
class ListNode {
     int val;
     ListNode next;
     ListNode(int x) {
         val = x;
         next = null;
     }
}

public class DetectStartOfLoopInLinkedList {
    public ListNode detectCycle(ListNode head) {
        ListNode ptrSlow = head;
        ListNode ptrFast = head;
        boolean loopFirst = true;
        while(ptrSlow != null && ptrFast != null && (ptrSlow != ptrFast || loopFirst) ) {
            loopFirst = false;
            ptrSlow = ptrSlow.next;
            if(ptrFast.next == null) {
                return null;
            } else {
                ptrFast = ptrFast.next.next;
            }
        }
        if(ptrSlow == null || ptrFast == null) {
            return null;
        } else {
            ListNode ptrSlow2 = head;
            while(ptrSlow != ptrSlow2) {
                ptrSlow = ptrSlow.next;
                ptrSlow2 = ptrSlow2.next;
            }
            return ptrSlow;
        }
    }
}
