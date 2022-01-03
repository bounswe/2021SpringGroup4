import React from 'react';
import './ModalEvent.css'

const ModalEvent = props => {
  const { isOpen, onClose, children } = props;
  if (!isOpen) return null;
  return (
    <>
      {isOpen && (
        <div className="modal-backdrop-event" onClick={onClose}>
          <div className="modal-content-wrapper-event">
            <div
              className="modal-content-event"
              key="modal"
              onClick={e => e.stopPropagation()}
            >
              {children}
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default ModalEvent;