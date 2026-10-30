"use strict";
const http = require("node:http"),
  fs = require("node:fs"),
  path = require("node:path");
const root = path.join(__dirname, "app");
const allowed = {
  "index.html": "text/html; charset=utf-8",
  "styles.css": "text/css; charset=utf-8",
  "domain.js": "text/javascript; charset=utf-8",
  "vault.js": "text/javascript; charset=utf-8",
  "report.js": "text/javascript; charset=utf-8",
  "app.js": "text/javascript; charset=utf-8",
  "mark.svg": "image/svg+xml",
};
const port = Number(process.env.MAZE_PREPARE_PORT || 4173);
if (!Number.isInteger(port) || port < 1024 || port > 65535)
  throw new Error("Use a port from 1024 to 65535.");
const server = http.createServer((req, res) => {
  const name = req.url === "/" ? "index.html" : req.url?.slice(1);
  res.setHeader("X-Content-Type-Options", "nosniff");
  res.setHeader("Referrer-Policy", "no-referrer");
  res.setHeader("Cache-Control", "no-store");
  res.setHeader(
    "Content-Security-Policy",
    "default-src 'none'; script-src 'self'; style-src 'self'; img-src 'self' data:; connect-src 'none'; object-src 'none'; base-uri 'none'; form-action 'none'; frame-ancestors 'none'",
  );
  res.setHeader(
    "Permissions-Policy",
    "camera=(), microphone=(), geolocation=()",
  );
  if (!["GET", "HEAD"].includes(req.method)) {
    res.writeHead(405);
    res.end();
    return;
  }
  if (!Object.hasOwn(allowed, name)) {
    res.writeHead(404);
    res.end("Not found");
    return;
  }
  const body = fs.readFileSync(path.join(root, name));
  res.setHeader("Content-Type", allowed[name]);
  res.setHeader("Content-Length", body.length);
  res.writeHead(200);
  res.end(req.method === "HEAD" ? undefined : body);
});
server.on("error", (err) => {
  process.stderr.write(
    err.code === "EADDRINUSE"
      ? "Port is already in use. Close the other local instance or set MAZE_PREPARE_PORT.\n"
      : "Could not start the loopback server.\n",
  );
  process.exitCode = 1;
});
server.listen(port, "127.0.0.1", () => {
  const url = `http://127.0.0.1:${port}`;
  process.stdout.write(
    `Maze Bank Prepare: ${url}\nFictional research preview. Press Ctrl+C to stop.\n`,
  );
  if (process.argv.includes("--open")) {
    const { execFile } = require("node:child_process");
    const launch =
      process.platform === "win32"
        ? ["rundll32.exe", ["url.dll,FileProtocolHandler", url]]
        : process.platform === "darwin"
          ? ["open", [url]]
          : ["xdg-open", [url]];
    execFile(launch[0], launch[1], { windowsHide: true }, () => {});
  }
});
