import React from 'react';

interface MenuButtonProps {
  icon: string;
  label: string;
  link?: string;
  onClick?: () => void;
  confirmMessage?: string;
}

const MenuButton: React.FC<MenuButtonProps> = ({ icon, label, link, onClick, confirmMessage }) => {
  const handleClick = () => {
    if (confirmMessage) {
      if (window.confirm(confirmMessage)) {
        if (onClick) onClick();
        else if (link) window.location.href = link;
      }
    } else {
      if (onClick) onClick();
      else if (link) window.location.href = link;
    }
  };

  return (
    <button
      onClick={handleClick}
      className="bg-white p-6 rounded-xl shadow hover:bg-gray-50 text-center w-full"
      type="button"
    >
      <span className="text-3xl">{icon}</span>
      <span className="block mt-2">{label}</span>
    </button>
  );
};

export default MenuButton;
