import { useNavigate } from 'react-router-dom';
import { authService } from '@shared/services';

export const Header = () => {
  const navigate = useNavigate();
  const user = authService.getCurrentUser();

  const handleLogout = async () => {
    await authService.logout();
    navigate('/login');
  };

  return (
    <header className="bg-white/95 backdrop-blur-sm border-b border-primary-100 px-3 sm:px-6 py-3 sm:py-4 flex items-center justify-between shadow-sm">
      <div className="flex items-center gap-3 sm:gap-4 flex-1">
        <img 
          src="/logo.png" 
          alt="Joyeria Nicki" 
          className="w-12 h-12 sm:w-16 sm:h-16 object-contain"
          onError={(e) => {
            e.currentTarget.style.display = 'none';
          }}
        />
        <div className="flex-1">
          <h2 className="text-base sm:text-xl font-semibold text-gray-800 tracking-wide">
            Bienvenido, {user?.nombre || 'Usuario'}
          </h2>
          <p className="text-xs sm:text-sm text-accent-600 font-medium">
            {user?.rol || 'Rol no definido'}
          </p>
        </div>
      </div>

      <div className="flex items-center gap-2 sm:gap-4">
        <div className="text-right mr-2 sm:mr-4 hidden sm:block">
          <p className="text-sm font-medium text-gray-700">{user?.nombre}</p>
          <p className="text-xs text-gray-500">{user?.email}</p>
        </div>
        
        <button
          onClick={handleLogout}
          className="btn-secondary flex items-center gap-2 text-sm sm:text-base px-3 sm:px-4 py-2"
        >
          Cerrar Sesión
        </button>
      </div>
    </header>
  );
};
