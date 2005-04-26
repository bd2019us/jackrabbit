/*
 * Copyright 2004-2005 The Apache Software Foundation or its licensors,
 *                     as applicable.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.core.state;

import org.apache.jackrabbit.core.QName;

/**
 * Identifies an <code>ItemStateManager</code> that allows updating
 * items.
 */
public interface UpdatableItemStateManager extends ItemStateManager {

    /**
     * Start an edit operation on items inside this manager. This
     * allows calling the operations defined below. At the end of
     * this operation, either {@link #update} or {@link #cancel}
     * must be invoked.
     *
     * @throws IllegalStateException if the manager is already in edit mode.
     */
    void edit() throws IllegalStateException;

    /**
     * Returns <code>true</code> if this manager is in edit mode i.e.
     * if an edit operation has been started by invoking {@link #edit},
     * otherwise returns <code>false</code>.
     *
     * @return <code>true</code> if this manager is in edit mode, otherwise
     *         <code>false</code>
     * @throws IllegalStateException if the manager is not in edit mode.
     */
    boolean inEditMode();

    /**
     * Creates a {@link NodeState} instance representing new,
     * i.e. not yet existing state. Call {@link #store}
     * on the returned object to make it persistent.
     *
     * @param uuid         node UUID
     * @param nodeTypeName qualified node type name
     * @param parentUUID   parent node's UUID
     * @return a node state
     * @throws IllegalStateException if the manager is not in edit mode.
     */
    NodeState createNew(String uuid, QName nodeTypeName,
                        String parentUUID) throws IllegalStateException;

    /**
     * Creates a {@link PropertyState} instance representing new,
     * i.e. not yet existing state. Call {@link #store}
     * on the returned object to make it persistent.
     *
     * @param propName   qualified property name
     * @param parentUUID parent node UUID
     * @return a property state
     * @throws IllegalStateException if the manager is not in edit mode.
     */
    PropertyState createNew(QName propName, String parentUUID)
            throws IllegalStateException;

    /**
     * Store an item state.
     *
     * @param state item state that should be stored
     * @throws IllegalStateException if the manager is not in edit mode.
     */
    void store(ItemState state) throws IllegalStateException;

    /**
     * Store a node references object
     *
     * @param refs node references object that should be stored
     * @throws IllegalStateException if the manager is not in edit mode.
     */
    void store(NodeReferences refs) throws IllegalStateException;

    /**
     * Destroy an item state.
     *
     * @param state item state that should be destroyed
     * @throws IllegalStateException if the manager is not in edit mode.
     */
    void destroy(ItemState state) throws IllegalStateException;

    /**
     * Cancel an update operation. This will undo all changes
     * made to objects inside this item state manager.
     *
     * @throws IllegalStateException if the manager is not in edit mode.
     */
    void cancel() throws IllegalStateException;

    /**
     * End an update operation. This will save all items
     * added to this update operation in a single step.
     * If this operation fails, no item will have been saved.
     *
     * @throws ItemStateException    if the operation failed
     * @throws IllegalStateException if the manager is not in edit mode.
     */
    void update() throws ItemStateException, IllegalStateException;
}
