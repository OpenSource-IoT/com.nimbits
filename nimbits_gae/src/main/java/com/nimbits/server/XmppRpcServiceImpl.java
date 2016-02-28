/*
 * Copyright 2016 Benjamin Sautner
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.nimbits.server;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.nimbits.client.model.user.User;
import com.nimbits.client.service.xmpp.XmppRpcService;
import com.nimbits.server.transaction.entity.service.EntityService;
import com.nimbits.server.transaction.user.service.UserService;
import com.nimbits.server.transaction.value.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;

@Service
public class XmppRpcServiceImpl extends RemoteServiceServlet implements XmppRpcService {

    @Autowired
    private UserService userService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private ValueService valueService;

    @Override
    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);


    }

    @Override
    public void sendInviteRpc() {

        User user = userService.getHttpRequestUser(entityService, valueService, getThreadLocalRequest());

//TODO do this on first login
        final JID jid = new JID(user.getEmail().getValue());
        final com.google.appengine.api.xmpp.XMPPService xmpp = XMPPServiceFactory.getXMPPService();
        xmpp.sendInvitation(jid);


    }
}
