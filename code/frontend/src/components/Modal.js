import React from 'react';
import './Modal.css'

const Modal = props => {
  const { isOpen, onClose, children } = props;
  if (!isOpen) return null;
  return (
    <>
      {isOpen && (
        <div className="modal-backdrop" onClick={onClose}>
          <div className="modal-content-wrapper">
            <div
              className="modal-content"
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

export default Modal;