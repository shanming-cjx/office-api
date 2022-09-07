package com.chenjx.office.api.activiti7;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *Activiti7服务任务：会议通知。
 */
public class NotifyMeetingService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("会议审批流程完成");
    }
}
