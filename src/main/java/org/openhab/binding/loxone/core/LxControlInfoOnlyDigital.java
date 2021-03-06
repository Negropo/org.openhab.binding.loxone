/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.loxone.core;

import org.openhab.binding.loxone.core.LxJsonApp3.LxJsonControl;

/**
 * An InfoOnlyDigital type of control on Loxone Miniserver.
 * <p>
 * According to Loxone API documentation, this control covers digital virtual states only. This control does not send
 * any commands to the Miniserver. It can be used to read a formatted representation of a digital virtual state.
 *
 * @author Pawel Pieczul - initial commit
 *
 */
public class LxControlInfoOnlyDigital extends LxControl {
    /**
     * A name by which Miniserver refers to digital virtual state controls
     */
    private static final String TYPE_NAME = "infoonlydigital";
    /**
     * InfoOnlyDigital has one state that can be on/off
     */
    private static final String STATE_ACTIVE = "active";

    private String textOn = null;
    private String textOff = null;

    /**
     * Create InfoOnlyDigital control object.
     *
     * @param client
     *            communication client used to send commands to the Miniserver
     * @param uuid
     *            control's UUID
     * @param json
     *            JSON describing the control as received from the Miniserver
     * @param room
     *            room to which control belongs
     * @param category
     *            category to which control belongs
     */
    LxControlInfoOnlyDigital(LxWsClient client, LxUuid uuid, LxJsonControl json, LxContainer room,
            LxCategory category) {
        super(client, uuid, json, room, category);
        if (json.details != null && json.details.text != null) {
            textOn = json.details.text.on;
            textOff = json.details.text.off;
        }
    }

    /**
     * Check if control accepts provided type name from the Miniserver
     *
     * @param type
     *            name of the type received from Miniserver
     * @return
     *         true if this control is suitable for this type
     */
    public static boolean accepts(String type) {
        return type.toLowerCase().equals(TYPE_NAME);
    }

    /**
     * Obtain current value of the virtual state, expressed in a format configured on the Miniserver
     *
     * @return
     *         string for on/off value of the state or null if current value is not compatible with this control
     */
    public String getFormattedValue() {
        LxControlState state = getState(STATE_ACTIVE);
        if (state != null) {
            if (state.getValue() == 0) {
                return textOff;
            } else if (state.getValue() == 1) {
                return textOn;
            }
        }
        return null;
    }

    /**
     * Obtain current value of an analog virtual state, expressed as a number
     *
     * @return
     *         value of the state or zero if current value is not compatible with this control
     */
    public double getValue() {
        LxControlState state = getState(STATE_ACTIVE);
        if (state != null) {
            return state.getValue();
        }
        return 0;
    }
}
