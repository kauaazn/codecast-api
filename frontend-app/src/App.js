import React, { useState } from 'react';
import Studios from './Studios';
import Hosts from './Hosts';
import Bookings from './Bookings';
import './index.css';

const IconStudio = () => (
  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M9 18V5l12-2v13"/>
    <circle cx="6" cy="18" r="3"/><circle cx="18" cy="16" r="3"/>
  </svg>
);

const IconHost = () => (
  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
    <circle cx="12" cy="7" r="4"/>
  </svg>
);

const IconCalendar = () => (
  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
    <line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/>
    <line x1="3" y1="10" x2="21" y2="10"/>
  </svg>
);

const PAGES = [
  { id: 'studios',  label: 'Estúdios',      Icon: IconStudio },
  { id: 'hosts',    label: 'Hosts',          Icon: IconHost },
  { id: 'bookings', label: 'Agendamentos',   Icon: IconCalendar },
];

function App() {
  const [page, setPage] = useState('studios');

  return (
    <div className="layout">
      <aside className="sidebar">
        <div className="sidebar-brand">
          <span className="brand-logo">CC</span>
          <div>
            <h1>CodeCast</h1>
            <span>Sistema de Agendamentos</span>
          </div>
        </div>
        <nav className="sidebar-nav">
          {PAGES.map(({ id, label, Icon }) => (
            <button
              key={id}
              className={page === id ? 'active' : ''}
              onClick={() => setPage(id)}
            >
              <span className="nav-icon"><Icon /></span>
              <span>{label}</span>
            </button>
          ))}
        </nav>
      </aside>

      <main className="main-content">
        {page === 'studios'  && <Studios />}
        {page === 'hosts'    && <Hosts />}
        {page === 'bookings' && <Bookings />}
      </main>
    </div>
  );
}

export default App;
