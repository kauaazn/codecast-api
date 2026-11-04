import React, { useState, useEffect } from 'react';

const API = 'http://localhost:8080';

const TIMEZONES = [
  { value: 'America/Sao_Paulo', label: 'América/São Paulo (BRT, UTC-3)' },
  { value: 'America/New_York',  label: 'América/Nova York (EST, UTC-5)' },
  { value: 'Europe/London',     label: 'Europa/Londres (GMT, UTC+0)' },
  { value: 'UTC',               label: 'UTC' },
];

const IconAlert = () => (
  <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/>
  </svg>
);

const IconCheck = () => (
  <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <polyline points="20 6 9 17 4 12"/>
  </svg>
);

const IconEmpty = () => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round">
    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
    <line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/>
    <line x1="3" y1="10" x2="21" y2="10"/>
  </svg>
);

function Bookings() {
  const [bookings, setBookings] = useState([]);
  const [studios, setStudios] = useState([]);
  const [hosts, setHosts] = useState([]);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [form, setForm] = useState({
    studioId: '', hostId: '', recordingDate: '',
    startTime: '', endTime: '', timezone: 'America/Sao_Paulo',
  });

  useEffect(() => {
    fetchBookings();
    fetchStudios();
    fetchHosts();
  }, []);

  async function fetchBookings() {
    try {
      const res = await fetch(`${API}/bookings`);
      const data = await res.json();
      setBookings(Array.isArray(data) ? data : []);
    } catch {
      setError('Não foi possível conectar à API.');
    }
  }

  async function fetchStudios() {
    try {
      const res = await fetch(`${API}/studios`);
      const data = await res.json();
      setStudios(Array.isArray(data) ? data : []);
    } catch {}
  }

  async function fetchHosts() {
    try {
      const res = await fetch(`${API}/hosts`);
      const data = await res.json();
      setHosts(Array.isArray(data) ? data : []);
    } catch {}
  }

  async function handleCreate(e) {
    e.preventDefault();
    setError('');
    setSuccess('');
    try {
      const res = await fetch(`${API}/bookings`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          studioId: parseInt(form.studioId),
          hostId: parseInt(form.hostId),
          recordingDate: form.recordingDate,
          startTime: form.startTime,
          endTime: form.endTime,
          timezone: form.timezone,
        }),
      });
      if (!res.ok) {
        const err = await res.json();
        setError(err.message || 'Não foi possível criar o agendamento.');
        return;
      }
      setSuccess('Agendamento criado com sucesso.');
      setForm({ studioId: '', hostId: '', recordingDate: '', startTime: '', endTime: '', timezone: 'America/Sao_Paulo' });
      fetchBookings();
    } catch {
      setError('Erro ao criar agendamento.');
    }
  }

  async function handleDelete(id) {
    setError('');
    setSuccess('');
    try {
      const res = await fetch(`${API}/bookings/${id}`, { method: 'DELETE' });
      if (!res.ok && res.status !== 204) {
        setError('Não foi possível cancelar o agendamento.');
        return;
      }
      setSuccess('Agendamento cancelado com sucesso.');
      fetchBookings();
    } catch {
      setError('Erro ao cancelar agendamento.');
    }
  }

  function formatTime(t) {
    return t ? t.slice(0, 5) : '—';
  }

  return (
    <>
      <div className="page-header">
        <h2>Agendamentos</h2>
        <p>Reserve um estúdio para gravação informando o host, data e horário.</p>
      </div>

      {error && (
        <div className="alert alert-error">
          <IconAlert />
          <div>
            <strong>Horário Indisponível</strong>
            {error}
          </div>
        </div>
      )}
      {success && (
        <div className="alert alert-success">
          <IconCheck />
          <span>{success}</span>
        </div>
      )}

      <div className="card">
        <div className="card-header">
          <h3>Nova reserva</h3>
        </div>
        <div className="card-body">
          <form onSubmit={handleCreate}>
            <div className="form-grid">
              <div className="field">
                <label>Estúdio</label>
                <select value={form.studioId} onChange={e => setForm({ ...form, studioId: e.target.value })} required>
                  <option value="">Selecione um estúdio</option>
                  {studios.map(s => <option key={s.id} value={s.id}>{s.name}</option>)}
                </select>
              </div>
              <div className="field">
                <label>Host</label>
                <select value={form.hostId} onChange={e => setForm({ ...form, hostId: e.target.value })} required>
                  <option value="">Selecione um host</option>
                  {hosts.map(h => <option key={h.id} value={h.id}>{h.name}</option>)}
                </select>
              </div>
              <div className="field">
                <label>Data de gravação</label>
                <input type="date" value={form.recordingDate} onChange={e => setForm({ ...form, recordingDate: e.target.value })} required />
              </div>
              <div className="field">
                <label>Fuso horário</label>
                <select value={form.timezone} onChange={e => setForm({ ...form, timezone: e.target.value })}>
                  {TIMEZONES.map(tz => <option key={tz.value} value={tz.value}>{tz.label}</option>)}
                </select>
              </div>
              <div className="field">
                <label>Horário de início</label>
                <input type="time" value={form.startTime} onChange={e => setForm({ ...form, startTime: e.target.value })} required />
              </div>
              <div className="field">
                <label>Horário de término</label>
                <input type="time" value={form.endTime} onChange={e => setForm({ ...form, endTime: e.target.value })} required />
              </div>
            </div>
            <div className="form-footer">
              <button type="submit" className="btn btn-primary">Confirmar reserva</button>
            </div>
          </form>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3>Reservas registradas</h3>
        </div>
        {bookings.length === 0 ? (
          <div className="empty-state">
            <div className="empty-state-icon"><IconEmpty /></div>
            <p>Nenhuma reserva registrada ainda.</p>
          </div>
        ) : (
          <table className="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Estúdio</th>
                <th>Host</th>
                <th>Data</th>
                <th>Horário (UTC)</th>
                <th>Fuso</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {bookings.map(b => (
                <tr key={b.id}>
                  <td className="cell-id">{b.id}</td>
                  <td className="cell-name">{b.studioName}</td>
                  <td>{b.hostName}</td>
                  <td>{b.recordingDate}</td>
                  <td><span className="badge badge-blue">{formatTime(b.startTime)} – {formatTime(b.endTime)}</span></td>
                  <td className="cell-muted" style={{ fontSize: 12 }}>{b.timezone}</td>
                  <td><button className="btn btn-danger" onClick={() => handleDelete(b.id)}>Cancelar</button></td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </>
  );
}

export default Bookings;
