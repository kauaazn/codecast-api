import React, { useState, useEffect } from 'react';

const API = 'http://localhost:8080';

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
    <path d="M9 18V5l12-2v13"/><circle cx="6" cy="18" r="3"/><circle cx="18" cy="16" r="3"/>
  </svg>
);

function Studios() {
  const [studios, setStudios] = useState([]);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [form, setForm] = useState({ name: '', maxCapacity: '', equipments: '' });

  useEffect(() => { fetchStudios(); }, []);

  async function fetchStudios() {
    try {
      const res = await fetch(`${API}/studios`);
      const data = await res.json();
      setStudios(Array.isArray(data) ? data : []);
    } catch {
      setError('Não foi possível conectar à API. Verifique se ela está rodando.');
    }
  }

  async function handleCreate(e) {
    e.preventDefault();
    setError('');
    setSuccess('');
    try {
      const res = await fetch(`${API}/studios`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          name: form.name,
          maxCapacity: parseInt(form.maxCapacity),
          equipments: form.equipments.split(',').map(s => s.trim()).filter(Boolean),
        }),
      });
      if (!res.ok) {
        const err = await res.json();
        setError(err.message || 'Erro ao criar estúdio.');
        return;
      }
      setSuccess('Estúdio cadastrado com sucesso.');
      setForm({ name: '', maxCapacity: '', equipments: '' });
      fetchStudios();
    } catch {
      setError('Erro ao criar estúdio.');
    }
  }

  async function handleDelete(id) {
    setError('');
    setSuccess('');
    try {
      const res = await fetch(`${API}/studios/${id}`, { method: 'DELETE' });
      if (!res.ok && res.status !== 204) {
        setError('Não foi possível remover o estúdio.');
        return;
      }
      setSuccess('Estúdio removido com sucesso.');
      fetchStudios();
    } catch {
      setError('Erro ao remover estúdio.');
    }
  }

  return (
    <>
      <div className="page-header">
        <h2>Estúdios</h2>
        <p>Gerencie as salas de gravação disponíveis no sistema.</p>
      </div>

      {error && (
        <div className="alert alert-error">
          <IconAlert />
          <span>{error}</span>
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
          <h3>Novo estúdio</h3>
        </div>
        <div className="card-body">
          <form onSubmit={handleCreate}>
            <div className="form-grid">
              <div className="field">
                <label>Nome</label>
                <input
                  placeholder="Ex: Estúdio A"
                  value={form.name}
                  onChange={e => setForm({ ...form, name: e.target.value })}
                  required
                />
              </div>
              <div className="field">
                <label>Capacidade máxima</label>
                <input
                  type="number"
                  min="1"
                  placeholder="Ex: 10"
                  value={form.maxCapacity}
                  onChange={e => setForm({ ...form, maxCapacity: e.target.value })}
                  required
                />
              </div>
              <div className="field span-2">
                <label>Equipamentos (separados por vírgula)</label>
                <input
                  placeholder="Ex: Microfone Shure, Mesa de som, Interface de áudio"
                  value={form.equipments}
                  onChange={e => setForm({ ...form, equipments: e.target.value })}
                />
              </div>
            </div>
            <div className="form-footer">
              <button type="submit" className="btn btn-primary">Cadastrar estúdio</button>
            </div>
          </form>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3>Estúdios cadastrados</h3>
        </div>
        {studios.length === 0 ? (
          <div className="empty-state">
            <div className="empty-state-icon"><IconEmpty /></div>
            <p>Nenhum estúdio cadastrado ainda.</p>
          </div>
        ) : (
          <table className="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Capacidade</th>
                <th>Equipamentos</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {studios.map(studio => (
                <tr key={studio.id}>
                  <td className="cell-id">{studio.id}</td>
                  <td className="cell-name">{studio.name}</td>
                  <td><span className="badge badge-blue">{studio.maxCapacity} pessoas</span></td>
                  <td className="cell-muted">{studio.equipments?.length ? studio.equipments.join(', ') : '—'}</td>
                  <td><button className="btn btn-danger" onClick={() => handleDelete(studio.id)}>Remover</button></td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </>
  );
}

export default Studios;
