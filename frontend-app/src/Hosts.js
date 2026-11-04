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
    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
  </svg>
);

function Hosts() {
  const [hosts, setHosts] = useState([]);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [form, setForm] = useState({ name: '', email: '', phone: '' });

  useEffect(() => { fetchHosts(); }, []);

  async function fetchHosts() {
    try {
      const res = await fetch(`${API}/hosts`);
      const data = await res.json();
      setHosts(Array.isArray(data) ? data : []);
    } catch {
      setError('Não foi possível conectar à API. Verifique se ela está rodando.');
    }
  }

  async function handleCreate(e) {
    e.preventDefault();
    setError('');
    setSuccess('');
    try {
      const res = await fetch(`${API}/hosts`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });
      if (!res.ok) {
        const err = await res.json();
        setError(err.message || 'Erro ao cadastrar host.');
        return;
      }
      setSuccess('Host cadastrado com sucesso.');
      setForm({ name: '', email: '', phone: '' });
      fetchHosts();
    } catch {
      setError('Erro ao cadastrar host.');
    }
  }

  async function handleDelete(id) {
    setError('');
    setSuccess('');
    try {
      const res = await fetch(`${API}/hosts/${id}`, { method: 'DELETE' });
      if (!res.ok && res.status !== 204) {
        setError('Não foi possível remover o host.');
        return;
      }
      setSuccess('Host removido com sucesso.');
      fetchHosts();
    } catch {
      setError('Erro ao remover host.');
    }
  }

  return (
    <>
      <div className="page-header">
        <h2>Hosts</h2>
        <p>Cadastre e gerencie os apresentadores autorizados a utilizar o sistema.</p>
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
          <h3>Novo host</h3>
        </div>
        <div className="card-body">
          <form onSubmit={handleCreate}>
            <div className="form-grid cols-3">
              <div className="field">
                <label>Nome completo</label>
                <input
                  placeholder="Ex: Ana Lima"
                  value={form.name}
                  onChange={e => setForm({ ...form, name: e.target.value })}
                  required
                />
              </div>
              <div className="field">
                <label>E-mail</label>
                <input
                  type="email"
                  placeholder="Ex: ana@email.com"
                  value={form.email}
                  onChange={e => setForm({ ...form, email: e.target.value })}
                  required
                />
              </div>
              <div className="field">
                <label>Telefone</label>
                <input
                  placeholder="Ex: (11) 99999-0000"
                  value={form.phone}
                  onChange={e => setForm({ ...form, phone: e.target.value })}
                  required
                />
              </div>
            </div>
            <div className="form-footer">
              <button type="submit" className="btn btn-primary">Cadastrar host</button>
            </div>
          </form>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3>Hosts cadastrados</h3>
        </div>
        {hosts.length === 0 ? (
          <div className="empty-state">
            <div className="empty-state-icon"><IconEmpty /></div>
            <p>Nenhum host cadastrado ainda.</p>
          </div>
        ) : (
          <table className="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>E-mail</th>
                <th>Telefone</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {hosts.map(host => (
                <tr key={host.id}>
                  <td className="cell-id">{host.id}</td>
                  <td className="cell-name">{host.name}</td>
                  <td className="cell-muted">{host.email}</td>
                  <td className="cell-muted">{host.phone}</td>
                  <td><button className="btn btn-danger" onClick={() => handleDelete(host.id)}>Remover</button></td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </>
  );
}

export default Hosts;
